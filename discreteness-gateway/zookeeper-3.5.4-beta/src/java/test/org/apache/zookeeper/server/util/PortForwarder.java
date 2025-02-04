/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 
 */
package org.apache.zookeeper.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility that does bi-directional forwarding between two ports.
 * Useful, for example, to simulate network failures.
 * Example:
 * 
 *   Server 1 config file:
 *           
 *      server.1=127.0.0.1:7301:7401;8201
 *      server.2=127.0.0.1:7302:7402;8202
 *      server.3=127.0.0.1:7303:7403;8203
 *   
 *   Server 2 and 3 config files:
 *           
 *      server.1=127.0.0.1:8301:8401;8201
 *      server.2=127.0.0.1:8302:8402;8202
 *      server.3=127.0.0.1:8303:8403;8203
 *
 *   Initially forward traffic between 730x and 830x and between 740x and 830x
 *   This way server 1 can communicate with servers 2 and 3
 *  ....
 *   
 *   List<PortForwarder> pfs = startForwarding();
 *  ....
 *   // simulate a network interruption for server 1
 *   stopForwarding(pfs);
 *  ....
 *   // restore connection 
 *   pfs = startForwarding();
 *
 *
 *  private List<PortForwarder> startForwarding() throws IOException {
 *      List<PortForwarder> res = new ArrayList<PortForwarder>();
 *      res.add(new PortForwarder(8301, 7301));
 *      res.add(new PortForwarder(8401, 7401));
 *      res.add(new PortForwarder(7302, 8302));
 *      res.add(new PortForwarder(7402, 8402));
 *      res.add(new PortForwarder(7303, 8303));
 *      res.add(new PortForwarder(7403, 8403));
 *      return res;
 *  }
 *  
 *  private void stopForwarding(List<PortForwarder> pfs) throws Exception {
 *       for (PortForwarder pf : pfs) {
 *           pf.shutdown();
 *       }
 *  }
 *  
 *
 */
public class PortForwarder extends Thread {
    private static final Logger LOG = LoggerFactory
            .getLogger(PortForwarder.class);

    private static class PortForwardWorker implements Runnable {

        private final InputStream in;
        private final OutputStream out;
        private final Socket toClose;
        private final Socket toClose2;

        PortForwardWorker(Socket toClose, Socket toClose2, InputStream in,
                OutputStream out) throws IOException {
            this.toClose = toClose;
            this.toClose2 = toClose2;
            this.in = in;
            this.out = out;
            // LOG.info("starting forward for "+toClose);
        }

        public void run() {
            Thread.currentThread().setName(toClose.toString() + "-->"
                    + toClose2.toString());
            byte[] buf = new byte[1024];
            try {
                while (true) {
                    try {
                        int read = this.in.read(buf);
                        if (read > 0) {
                            try {
                                this.out.write(buf, 0, read);
                            } catch (IOException e) {
                                LOG.warn("exception during write", e);
                                try {
                                    toClose.close();
                                } catch (IOException ex) {
                                    // ignore
                                }
                                try {
                                    toClose2.close();
                                } catch (IOException ex) {
                                    // ignore
                                }
                                break;
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        LOG.error("socket timeout", e);
                    }
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                LOG.warn("Interrupted", e);
                try {
                    toClose.close();
                } catch (IOException ex) {
                    // ignore
                }
                try {
                    toClose2.close();
                } catch (IOException ex) {
                    // ignore silently
                }
            } catch (SocketException e) {
                if (!"Socket closed".equals(e.getMessage())) {
                    LOG.error("Unexpected exception", e);
                }
            } catch (IOException e) {
                LOG.error("Unexpected exception", e);
            }
            LOG.info("Shutting down forward for " + toClose);
        }

    }

    private volatile boolean stopped = false;
    private ExecutorService workers = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    private final int to;

    public PortForwarder(int from, int to) throws IOException {
        this.to = to;
        serverSocket = new ServerSocket(from);
        serverSocket.setSoTimeout(30000);
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!stopped) {
                Socket sock = null;
                try {
                    LOG.info("accepting socket local:"
                            + serverSocket.getLocalPort() + " to:" + to);
                    sock = serverSocket.accept();
                    LOG.info("accepted: local:" + sock.getLocalPort()
                            + " from:" + sock.getPort()
                            + " to:" + to);
                    Socket target = null;
                    int retry = 10;
                    while(sock.isConnected()) {
                        try {
                            target = new Socket("localhost", to);
                            break;
                        } catch (IOException e) {
                            if (retry == 0) {
                               throw e;
                            }
                            LOG.warn("connection failed, retrying(" + retry
                                    + "): local:" + sock.getLocalPort()
                                    + " from:" + sock.getPort()
                                    + " to:" + to, e);
                        }
                        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                        retry--;
                    }
                    LOG.info("connected: local:" + sock.getLocalPort()
                            + " from:" + sock.getPort()
                            + " to:" + to);
                    sock.setSoTimeout(30000);
                    target.setSoTimeout(30000);
                    this.workers.execute(new PortForwardWorker(sock, target,
                            sock.getInputStream(), target.getOutputStream()));
                    this.workers.execute(new PortForwardWorker(target, sock,
                            target.getInputStream(), sock.getOutputStream()));
                } catch (SocketTimeoutException e) {               	
                    LOG.warn("socket timed out local:" 
                            + (sock != null ? sock.getLocalPort(): "")
                            + " from:" + (sock != null ? sock.getPort(): "")
                            + " to:" + to, e);
                } catch (ConnectException e) {
                    LOG.warn("connection exception local:"
                            + (sock != null ? sock.getLocalPort(): "")
                            + " from:" + (sock != null ? sock.getPort(): "")
                            + " to:" + to, e);
                    sock.close();
                } catch (IOException e) {
                    if (!"Socket closed".equals(e.getMessage())) {
                        LOG.warn("unexpected exception local:" 
                        		+ (sock != null ? sock.getLocalPort(): "")
                                + " from:" + (sock != null ? sock.getPort(): "")
                                + " to:" + to, e);
                        throw e;
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Unexpected exception to:" + to, e);
        } catch (InterruptedException e) {
            LOG.error("Interrupted to:" + to, e);
        }
    }

    public void shutdown() throws Exception {
        this.stopped = true;
        this.serverSocket.close();
        this.workers.shutdownNow();
        try {
            if (!this.workers.awaitTermination(5, TimeUnit.SECONDS)) {
                new Exception(
                        "Failed to stop forwarding within 5 seconds");
            }
        } catch (InterruptedException e) {
            new Exception("Failed to stop forwarding");
        }
        this.join();
    }
}
