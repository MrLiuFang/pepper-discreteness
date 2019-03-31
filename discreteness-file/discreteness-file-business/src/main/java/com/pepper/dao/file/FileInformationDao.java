package com.pepper.dao.file;

import org.springframework.data.jpa.repository.Query;
import com.pepper.core.base.BaseDao;
import com.pepper.model.file.File;

/**
 * 
 * @author mrliu
 *
 */
public interface FileInformationDao extends BaseDao<File> {

	@Query(" from File where fileId=?1 ")
	File queryByFileId(String fileId);
}
