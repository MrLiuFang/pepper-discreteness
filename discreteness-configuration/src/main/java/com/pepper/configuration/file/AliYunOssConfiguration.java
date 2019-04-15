package com.pepper.configuration.file;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;

/**
 * 阿里云对象存贮
 * @author mrliu
 *
 */
@Configuration
@ConditionalOnProperty(name = "file.storage.type", havingValue = "aliyun")
@ConditionalOnClass(value={ClientConfiguration.class,OSSClient.class})
public class AliYunOssConfiguration {
	
	private AliYunOssProperties aliYunOssProperties;
	
	public AliYunOssConfiguration(AliYunOssProperties aliYunOssProperties) {
		super();
		this.aliYunOssProperties = aliYunOssProperties;
	}

	public OSSClient oSSClient(){
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		DefaultCredentialProvider defaultCredentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(aliYunOssProperties.getAccessKeyId(), aliYunOssProperties.getAccessKeySecret());
		OSSClient ossClient = new OSSClient(aliYunOssProperties.getEndpoint(), defaultCredentialProvider,clientConfiguration);
		return ossClient;
	}
}
