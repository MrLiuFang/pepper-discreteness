package com.pepper.service.file.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.pepper.core.exception.BusinessException;



@Component
@ConditionalOnBean(value={OSSClient.class,FileBeanFactory.class})
@ConditionalOnProperty(prefix = "file", name = "storage.type", havingValue = AliyunOSS.STORAGE_TYPE_NAME, matchIfMissing = true)
public class AliyunOSS implements IFile {
	
	public static final String STORAGE_TYPE_NAME = "aliyun";

	@Autowired
	private Environment env;
	
	@Resource
	private FileBeanFactory fileBeanFactory;
	
	@Resource
	private OSSClient ossClient;

	public AliyunOSS() {
	}

	@Override
	public String add(File file) {

		// 上传文件
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		ossClient.putObject(env.getProperty("aliyunoss.bucket"), uuid, file);
		// 关闭client
//		ossClient.shutdown();
		return uuid;
	}

	@Override
	public String getStorageTypeName() {
		return STORAGE_TYPE_NAME;
	}

	@Override
	public File getFile(String key) {
		new BusinessException("Aliyun OSS文件下载还没实现");
		return null;
	}

	@Override
	public String getUrl(com.pepper.model.file.File file) {

		return env.getProperty("aliyunoss.domain") + "/" + file.getUrl();
	}

	@Override
	public String getUrl(com.pepper.model.file.File file, String pix) {
		return env.getProperty("aliyunoss.domain") + "/" + file.getUrl() + "?x-oss-process=image/resize,h_"
				+ pix;
	}

	@Override
	public InputStream getFileInputStream(String key) {
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

	private OSSObject getOSSObject(OSSClient ossClient, String key) {
		OSSObject object = ossClient.getObject(env.getProperty("aliyunoss.bucket"), key);
		return object;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		fileBeanFactory.setBean(getStorageTypeName(), this);
	}

}
