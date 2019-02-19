package com.pepper.service.file.impl;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import com.pepper.core.base.impl.BaseServiceImpl;
import com.pepper.dao.file.FileInformationDao;
import com.pepper.model.file.FileInformation;
import com.pepper.service.file.FileInformationService;

@Service(interfaceClass = FileInformationService.class)
public class FileInformationServiceImpl extends BaseServiceImpl<FileInformation> implements FileInformationService {

	@Resource
	private FileInformationDao fileInformationDao;


	@Override
	public FileInformation findByFileId(String fileId) {
		return fileInformationDao.queryByFileId(fileId);
	}

}
