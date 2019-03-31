package com.pepper.service.file;

import com.pepper.core.base.BaseService;
import com.pepper.model.file.File;

/**
 * 文件表对象
 */
public interface FileInformationService extends BaseService<File> {

	/**
	 * 根据fileId获取记录
	 * 
	 * @param fileId
	 * @return
	 */
	File findByFileId(String fileId);

}
