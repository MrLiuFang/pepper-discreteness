package com.pepper.service.file.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import com.pepper.core.exception.BusinessException;
import com.pepper.model.file.FileInformation;


@Component
public class AliyunOSS implements IFile {

	@Autowired
	private Environment env;

	public AliyunOSS() {
	}

	@Override
	public String add(File file) {
		// 创建OSSClient实例
		OSSClient ossClient = getOSSClient();

		// 上传文件
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		ossClient.putObject(env.getProperty("aliyunoss.bucket"), uuid, file);
		// 关闭client
		ossClient.shutdown();
		// return new String(Base64.encodeBase64(uuid.getBytes()));
		return uuid;
	}

	@Override
	public String getLocationName() {
		return "aliyun";
	}

	@Override
	public File getFile(String key) {
		new BusinessException("Aliyun OSS文件下载还没实现");
		return null;
	}

	@Override
	public String getUrl(FileInformation fileInformation) {

		return env.getProperty("aliyunoss.domain") + "/" + fileInformation.getUrl();
	}

	@Override
	public String getUrl(FileInformation fileInformation, String pix) {
		return env.getProperty("aliyunoss.domain") + "/" + fileInformation.getUrl() + "?x-oss-process=image/resize,h_"
				+ pix;
	}

	@Override
	public InputStream getFileInputStream(String key) {
		OSSClient ossClient = getOSSClient();
		OSSObject object = getOSSObject(ossClient, key);
		if (object != null) {
			return object.getObjectContent();
		} else {
			return new InputStream() {
				@Override
				public int read() throws IOException {
					return 0;
				}
			};
		}
	}

	private OSSClient getOSSClient() {
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		DefaultCredentialProvider defaultCredentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(
				env.getProperty("aliyunoss.accessKeyId"), env.getProperty("aliyunoss.accessKeySecret"));
		OSSClient ossClient = new OSSClient(env.getProperty("aliyunoss.endpoint"), defaultCredentialProvider,
				clientConfiguration);
		return ossClient;
	}

	private OSSObject getOSSObject(OSSClient ossClient, String key) {
		OSSObject object = ossClient.getObject(env.getProperty("aliyunoss.bucket"), key);
		return object;
	}

}
