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
package org.apache.zookeeper.cli;

import java.util.List;
import org.apache.commons.cli.*;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

/**
 * setAcl command for cli
 */
public class SetAclCommand extends CliCommand {

    private static Options options = new Options();
    private String[] args;
    private CommandLine cl;

    {
        options.addOption("s", false, "stats");
        options.addOption("v", true, "version");
    }

    public SetAclCommand() {
        super("setAcl", "[-s] [-v version] path acl");
    }

    @Override
    public CliCommand parse(String[] cmdArgs) throws CliParseException {
        Parser parser = new PosixParser();
        try {
            cl = parser.parse(options, cmdArgs);
        } catch (ParseException ex) {
            new CliParseException(ex);
        }
        args = cl.getArgs();
        if (args.length < 3) {
            new CliParseException(getUsageStr());
        }

        return this;
    }

    @Override
    public boolean exec() throws CliException {
        String path = args[1];
        String aclStr = args[2];
        List<ACL> acl = AclParser.parse(aclStr);
        int version;
        if (cl.hasOption("v")) {
            version = Integer.parseInt(cl.getOptionValue("v"));
        } else {
            version = -1;
        }
        try {
            Stat stat = zk.setACL(path, acl, version);
            if (cl.hasOption("s")) {
                new StatPrinter(out).print(stat);
            }
        } catch (IllegalArgumentException ex) {
            new MalformedPathException(ex.getMessage());
        } catch (KeeperException|InterruptedException ex) {
            new CliWrapperException(ex);
        }

        return false;

    }
}
