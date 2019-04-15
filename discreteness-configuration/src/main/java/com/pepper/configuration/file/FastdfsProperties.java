package com.pepper.configuration.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author mrliu
 *
 */
@ConfigurationProperties(prefix = "fastdfs", ignoreUnknownFields = true)
public class FastdfsProperties {

	private String httpSecretKey;

	private String trackerServers;

	private String connectTimeoutInSeconds;

	private String networkTimeoutInSeconds;

	private String charset;

	private String httpAntiStealToken;

	private String httpTrackerTttpPort;

	public String getHttpSecretKey() {
		return httpSecretKey;
	}

	public void setHttpSecretKey(String httpSecretKey) {
		this.httpSecretKey = httpSecretKey;
	}

	public String getTrackerServers() {
		return trackerServers;
	}

	public void setTrackerServers(String trackerServers) {
		this.trackerServers = trackerServers;
	}

	public String getConnectTimeoutInSeconds() {
		return connectTimeoutInSeconds;
	}

	public void setConnectTimeoutInSeconds(String connectTimeoutInSeconds) {
		this.connectTimeoutInSeconds = connectTimeoutInSeconds;
	}

	public String getNetworkTimeoutInSeconds() {
		return networkTimeoutInSeconds;
	}

	public void setNetworkTimeoutInSeconds(String networkTimeoutInSeconds) {
		this.networkTimeoutInSeconds = networkTimeoutInSeconds;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getHttpAntiStealToken() {
		return httpAntiStealToken;
	}

	public void setHttpAntiStealToken(String httpAntiStealToken) {
		this.httpAntiStealToken = httpAntiStealToken;
	}

	public String getHttpTrackerTttpPort() {
		return httpTrackerTttpPort;
	}

	public void setHttpTrackerTttpPort(String httpTrackerTttpPort) {
		this.httpTrackerTttpPort = httpTrackerTttpPort;
	}

}
