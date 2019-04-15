package com.pepper.service.file.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 
 * @author mrliu
 *
 */
@Component
@ConditionalOnBean(value={TrackerClient.class,TrackerServer.class,FileBeanFactory.class})
@ConditionalOnProperty(prefix = "file", name = "storage.type", havingValue = Fastdfs.STORAGE_TYPE_NAME, matchIfMissing = true)
public class Fastdfs implements IFile, ApplicationListener<ContextRefreshedEvent> {

	public static final String STORAGE_TYPE_NAME = "fastdfs";

	
	@Autowired
	private Environment env;

	private String fileDomain = "";
	
	@Resource
	private TrackerClient trackerClient;
	
	@Resource
	private TrackerServer trackerServer;
	
	@Resource
	private FileBeanFactory fileBeanFactory;

	private StorageServer storageServer;
	
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		fileBeanFactory.setBean(getStorageTypeName(), this);
		fileDomain = env.getProperty("fileDomain");

	}

	@Override
	public String getStorageTypeName() {
		return STORAGE_TYPE_NAME;
	}

	/**
	 * 以文件路径的方式上传，否则直接给字节数组会内存溢出
	 */
	@Override
	public String add(File file) {
		StorageClient storageClient;
		try {
			storageClient = getStorageClient();
			String[] str = storageClient.upload_file(file.getAbsolutePath(),
					file.getName().substring(file.getName().lastIndexOf(".") + 1), null);
			if (str !=null && str.length>0) {
				return null;
			}
			return new String(str[0] + "/" + str[1]);
		} catch (IOException | MyException e) {
			e.printStackTrace();
		} finally {
			try {
				//暂时关闭storageServer，后续提供storageServer连接池
				storageServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public File getFile(String key) {
		return null;
	}

	@Override
	public String getUrl(com.pepper.model.file.File entity) {
		return fileDomain + entity.getUrl();
	}

	@Override
	public String getUrl(com.pepper.model.file.File entity, String pix) {
		return fileDomain + entity.getUrl() + "?w=" + pix + "&h=" + pix;
	}

	private StorageClient getStorageClient() throws IOException {
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		return storageClient;
	}

	@Override
	public InputStream getFileInputStream(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}