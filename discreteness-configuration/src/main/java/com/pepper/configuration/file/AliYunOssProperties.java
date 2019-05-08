package com.pepper.configuration.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author mrliu
 *
 */
@ConfigurationProperties(prefix = "aliyunoss",ignoreUnknownFields = true)
public class AliYunOssProperties {

	private String bucket;
	
	private String accessKeyId;
	
	private String accessKeySecret;
	
	private String endpoint;

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	
}
