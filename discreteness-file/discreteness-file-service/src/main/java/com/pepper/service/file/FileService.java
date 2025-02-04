package com.pepper.service.file;

import java.util.List;
import java.util.Map;

import com.pepper.model.file.File;

/**
 * 文件上传
 *
 * @author mrliu
 *
 */
public interface FileService {
	
	/**
	 * 
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String addFile(byte[] fileByte, String fileName);

	/**
	 * 获取文件下载URL
	 *
	 * @param fileId
	 * @return
	 */
	public String getUrl(String fileId);
	
	public File getFile(String fileId);

	/**
	 * 用于组装数据给前端上传控件回显。如传入"id1;id2;idn",得到[{"id":id1,"url":url1},{"id":id2,"url":url2},{"id":id3,"url":url3}]
	 *
	 * @param qualificationsImage
	 * @return
	 */
	public List<Map<String, Object>> renderFiles(String... imgId);

}
