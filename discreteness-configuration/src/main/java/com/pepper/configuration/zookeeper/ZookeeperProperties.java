package com.pepper.configuration.zookeeper;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author mrliu
 *
 */
@ConfigurationProperties(prefix = "zookeeper",ignoreUnknownFields = true)
public class ZookeeperProperties {
	
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
