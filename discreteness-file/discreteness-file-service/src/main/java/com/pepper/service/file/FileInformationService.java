package com.pepper.service.file;

import com.pepper.core.base.BaseService;
import com.pepper.model.file.FileInformation;

/**
 * 文件表对象
 */
public interface FileInformationService extends BaseService<FileInformation> {

	/**
	 * 根据fileId获取记录
	 * 
	 * @param fileId
	 * @return
	 */
	FileInformation findByFileId(String fileId);

}
