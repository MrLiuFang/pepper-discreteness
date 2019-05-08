package com.pepper.dao.file;

import com.pepper.core.base.BaseDao;
import com.pepper.model.file.File;

/**
 * 
 * @author mrliu
 *
 */
public interface FileDao extends BaseDao<File> {

	File findByFileId(String fileId);
}
