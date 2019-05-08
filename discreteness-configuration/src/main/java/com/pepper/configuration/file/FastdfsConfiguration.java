package com.pepper.configuration.file;

import java.io.IOException;
import java.util.Properties;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@ConditionalOnProperty(name = "file.storage.type", havingValue = "fastddfs")
@ConditionalOnClass(value={TrackerClient.class,TrackerServer.class,StorageServer.class})
public class FastdfsConfiguration {
	
	private FastdfsProperties fastdfsProperties;

	public FastdfsConfiguration(FastdfsProperties fastdfsProperties) {
		super();
		this.fastdfsProperties = fastdfsProperties;
	}
	
	@Bean
	public TrackerClient trackerClient() throws IOException, MyException{
		Properties properties = new Properties();
		properties.setProperty("fastdfs.http_secret_key", fastdfsProperties.getHttpSecretKey());
		properties.setProperty("fastdfs.tracker_servers", fastdfsProperties.getTrackerServers());
		properties.setProperty("fastdfs.connect_timeout_in_seconds",fastdfsProperties.getConnectTimeoutInSeconds());
		properties.setProperty("fastdfs.network_timeout_in_seconds",fastdfsProperties.getNetworkTimeoutInSeconds());
		properties.setProperty("fastdfs.charset",fastdfsProperties.getCharset());
		properties.setProperty("fastdfs.http_anti_steal_token",fastdfsProperties.getHttpAntiStealToken());
		properties.setProperty("fastdfs.http_tracker_http_port",fastdfsProperties.getHttpTrackerTttpPort());
		ClientGlobal.initByProperties(properties);
		return  new TrackerClient();
	}
	
	@Bean
	@DependsOn(value={"trackerClient"})
	public TrackerServer trackerServer(TrackerClient trackerClient) throws IOException{
		return trackerClient.getConnection();
	}
	
}
