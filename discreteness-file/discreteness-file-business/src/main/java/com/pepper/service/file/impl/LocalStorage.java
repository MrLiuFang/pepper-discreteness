package com.pepper.service.file.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class LocalStorage implements IFile {
	
	public static final String STORAGE_TYPE_NAME = "localStorage";

	@Autowired
	private Environment env;
	
	@Resource
	private FileBeanFactory fileBeanFactory;

	@Override
	public String getStorageTypeName() {
		return STORAGE_TYPE_NAME;
	}

	@Override
	public String add(File file) {
		String fileId = UUID.randomUUID().toString().replace("-", "");
		String path = env.getProperty("file.local.storage.path", "/file")+"/"+fileId+file.getName();
		try {
			FileUtils.copyFile(file,  new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	@Override
	public File getFile(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getFileInputStream(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrl(com.pepper.model.file.File file) {
		return file.getUrl();
	}

	@Override
	public String getUrl(com.pepper.model.file.File file, String pix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		fileBeanFactory.setBean(getStorageTypeName(), this);
		
	}

}
