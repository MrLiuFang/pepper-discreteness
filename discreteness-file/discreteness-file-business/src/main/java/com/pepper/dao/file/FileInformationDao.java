package com.pepper.dao.file;

import org.springframework.data.jpa.repository.Query;
import com.pepper.core.base.BaseDao;
import com.pepper.model.file.FileInformation;

/**
 * 
 * @author mrliu
 *
 */
public interface FileInformationDao extends BaseDao<FileInformation> {

	@Query(" from FileInformation where fileId=?1 ")
	FileInformation queryByFileId(String fileId);
}
