package com.pepper.service.file.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.pepper.dao.file.FileDao;
import com.pepper.service.file.FileService;

/**
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass=FileService.class)
public class FileServiceImpl implements FileService {

	@Resource
	private FileBeanFactory fileBeanFactory;
	
	@Autowired
	private Environment env;
	
	@Resource
	private FileDao fileDao;
	
	@Override
	public String addFile(byte[] fileByte, String fileName) {
		String fileStorageId = null;
		String folder=System.getProperty("java.io.tmpdir");
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(fileName.lastIndexOf(".")<=0||!StringUtils.hasText(suffix)){
			fileName = fileName +".file";
		}
		File file = new File(folder+"/"+fileName);
		try {
			// 建立输出字节流
			FileOutputStream fos = new FileOutputStream(file);
			// 用FileOutputStream 的write方法写入字节数组
			fos.write(fileByte);
			fos.close();
			IFile iFile = fileBeanFactory.getBean(env.getProperty("file.storage.type"));
			if(iFile != null){
				fileStorageId = iFile.add(file);
				fileStorageId = recordFileInfo(fileByte.length,fileStorageId,fileName,iFile.getStorageTypeName());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}
		return fileStorageId;
	}
	
	private String recordFileInfo(Integer fileLength, String fileStorageId,String fileName,String storageTypeName){
		String fileStorageType = env.getProperty("file.storage.type");
		if (StringUtils.hasText(fileStorageId)) {
			com.pepper.model.file.File entity = new com.pepper.model.file.File();
			String fileId = (Fastdfs.STORAGE_TYPE_NAME.equals(fileStorageType) || LocalStorage.STORAGE_TYPE_NAME.equals(fileStorageType) ) ? UUID.randomUUID().toString().replaceAll("-", ""): fileStorageId;
			entity.setFileId(fileId);
			entity.setName(fileName);
			entity.setSize(fileLength / 1024);
			entity.setStorageTypeName(storageTypeName);
			entity.setUrl(fileStorageId);
			fileDao.save(entity);
			return fileId;
		}
		return fileStorageId;
	}

	@Override
	public String getUrl(String fileId) {
		if(!org.springframework.util.StringUtils.hasText(fileId)) {
			return "";
		}
		com.pepper.model.file.File entity = fileDao.findByFileId(fileId);
		if (entity != null) {
			IFile ifile = fileBeanFactory.getBean(entity.getStorageTypeName());
			if (ifile != null) {
				return ifile.getUrl(entity);
			}
		}
		return "";
	}

	@Override
	public List<Map<String, Object>> renderFiles(String... imgId) {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = null;
		if (imgId.length>0) {
			for (String id : imgId) {
				if(StringUtils.hasText(id)){
					temp = new HashMap<String, Object>();
					temp.put("id", imgId);
					temp.put("url", getUrl(id));
					res.add(temp);
				}
			}
		}
		return res;
	}


}
