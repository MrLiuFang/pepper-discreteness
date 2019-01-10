package com.pepper.service.file.impl;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pepper.core.exception.BusinessException;
import com.pepper.model.file.FileInformation;

@Component
public class UrlSave implements IFile {

	@Override
	public String add(File file) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}

	@Override
	public String getLocationName() {
		return "urlSave";
	}

	@Override
	public File getFile(String key) {
		new BusinessException("UrlSave文件下载还没实现");
		return null;
	}

	@Override
	public String getUrl(FileInformation fileInformation) {

		return fileInformation.getUrl();
	}

	@Override
	public String getUrl(FileInformation fileInformation, String pix) {
		return fileInformation.getUrl();
	}

	@Override
	public InputStream getFileInputStream(String key) {
		return null;
	}

}
