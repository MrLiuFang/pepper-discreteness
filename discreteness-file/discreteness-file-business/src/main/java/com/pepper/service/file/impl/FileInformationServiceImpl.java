package com.pepper.service.file.impl;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import com.pepper.core.base.impl.BaseServiceImpl;
import com.pepper.dao.file.FileDao;
import com.pepper.model.file.File;
import com.pepper.service.file.FileInformationService;

@Service(interfaceClass = FileInformationService.class)
public class FileInformationServiceImpl extends BaseServiceImpl<File> implements FileInformationService {

	@Resource
	private FileDao fileDao;


	@Override
	public File findByFileId(String fileId) {
		return fileDao.queryByFileId(fileId);
	}

}
