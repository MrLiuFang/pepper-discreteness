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
import com.pepper.dao.file.FileInformationDao;
import com.pepper.model.file.FileInformation;
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
	private FileInformationDao fileInformationDao;
	
	private static IFile ifile;

	public String add(File file) {
		if (file == null) {
			new BusinessException("文件不能为空");
		}
		String key = getIFile().add(file);
		String fileId = null;
		fileId = "fastdfs".equals(env.getProperty("file.action")) ? UUID.randomUUID().toString().replaceAll("-", "")
				: key;
		if (StringUtils.isNotBlank(key)) {
			FileInformation fileInformation = new FileInformation();
			fileInformation.setFileId(fileId);
			fileInformation.setName(file.getName());
			Long lengthKb = file.length() / 1024;
			fileInformation.setSize(lengthKb.intValue());
			fileInformation.setLocation(getIFile().getLocationName());
			fileInformation.setUrl(key);
			fileInformationDao.save(fileInformation);
		}
		file.delete();
		return fileId;
	}

	public String addUrl(String url) {
		if (StringUtils.isBlank(url)) {
			new BusinessException("url不能为空");
		}
		IFile ifile = getIFile("urlSave");
		String fileId = UUID.randomUUID().toString().replaceAll("-", "");
		FileInformation fileInformation = new FileInformation();
		fileInformation.setFileId(fileId);
		fileInformation.setName("外部链接");
		Long lengthKb = 0L;
		fileInformation.setSize(lengthKb.intValue());
		fileInformation.setLocation(ifile.getLocationName());
		fileInformation.setUrl(url);
		fileInformationDao.save(fileInformation);
		return fileId;
	}

	public InputStream get(String id) {
		return getIFile().getFileInputStream(id);
	}

	public String getUrl(String fileId) {
		FileInformation fileInformation = fileInformationDao.queryByFileId(fileId);
		if (fileInformation != null) {
			IFile ifile = getIFile(fileInformation.getLocation());
			if (ifile != null) {
				return ifile.getUrl(fileInformation);
			}
		}
		return "";
	}

	private IFile getIFile() {
		String fileAction = env.getProperty("file.action");
		return getIFile(fileAction);
	}

	private IFile getIFile(String location) {
		synchronized (location) {
			if(ifile == null){
				if (location.equals("fastdfs")) {
					ifile = (IFile) SpringContextUtil.getBean("fastdfs");
				} else if (location.equals("aliyun")) {
					ifile = (IFile) (IFile) SpringContextUtil.getBean("aliyunOSS");
				} else if (location.equals("urlSave")) {
					ifile = (IFile) (IFile) SpringContextUtil.getBean("urlSave");
				}
			}
		}
		return ifile;
	}

}
