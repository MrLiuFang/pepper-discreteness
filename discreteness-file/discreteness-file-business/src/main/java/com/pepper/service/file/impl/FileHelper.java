package com.pepper.service.file.impl;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.pepper.core.exception.BusinessException;
import com.pepper.dao.file.FileDao;
import com.pepper.util.SpringContextUtil;

/**
 *
 * @author mrliu
 *
 */
@Component
public class FileHelper {

	@Autowired
	private Environment env;

	@Resource
	private FileDao fileDao;
	
	private static IFile ifile;

	public String add(final File file) {
		if (file == null) {
			new BusinessException("文件不能为空");
		}
		String url = getIFile().add(file);
		String fileId = null;
		fileId = ("fastdfs".equals(env.getProperty("file.storage.type")) || "local_storage".equals(env.getProperty("file.storage.type")) ) ? UUID.randomUUID().toString().replaceAll("-", "")
				: url;
		if (StringUtils.isNotBlank(url)) {
			com.pepper.model.file.File entity = new com.pepper.model.file.File();
			entity.setFileId(fileId);
			entity.setName(file.getName());
			Long lengthKb = file.length() / 1024;
			entity.setSize(lengthKb.intValue());
			entity.setLocation(getIFile().getLocationName());
			entity.setUrl(url);
			fileDao.save(entity);
		}
		file.delete();
		return fileId;
	}

	public String addUrl(final String url) {
		if (StringUtils.isBlank(url)) {
			new BusinessException("url不能为空");
		}
		IFile ifile = getIFile("urlSave");
		String fileId = UUID.randomUUID().toString().replaceAll("-", "");
		com.pepper.model.file.File entity = new com.pepper.model.file.File();
		entity.setFileId(fileId);
		entity.setName("外部链接");
		Long lengthKb = 0L;
		entity.setSize(lengthKb.intValue());
		entity.setLocation(ifile.getLocationName());
		entity.setUrl(url);
		fileDao.save(entity);
		return fileId;
	}

	public InputStream get(final String id) {
		return getIFile().getFileInputStream(id);
	}

	public String getUrl(final String fileId) {
		if(!org.springframework.util.StringUtils.hasText(fileId)) {
			return "";
		}
		com.pepper.model.file.File entity = fileDao.queryByFileId(fileId);
		if (entity != null) {
			IFile ifile = getIFile(entity.getLocation());
			if (ifile != null) {
				return ifile.getUrl(entity);
			}
		}
		return "";
	}

	private IFile getIFile() {
		String storageType = env.getProperty("file.storage.type");
		return getIFile(storageType);
	}

	private IFile getIFile(final String storageType) {
		synchronized (storageType) {
			if(ifile == null){
				if (storageType.equals("fastdfs")) {
					ifile = (IFile) SpringContextUtil.getBean("fastdfs");
				} else if (storageType.equals("aliyun")) {
					ifile = (IFile) (IFile) SpringContextUtil.getBean("aliyunOSS");
				} else if (storageType.equals("urlSave")) {
					ifile = (IFile) (IFile) SpringContextUtil.getBean("urlSave");
				}else if (storageType.equals("local_storage")) {
					ifile = (IFile) (IFile) SpringContextUtil.getBean("localStorage");
				}
			}
		}
		return ifile;
	}

}
