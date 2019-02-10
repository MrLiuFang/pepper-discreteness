package com.pepper.service.file.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pepper.model.file.FileInformation;
/**
 * 
 * @author mrliu
 *
 */
@Component
public class Fastdfs implements IFile, ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Environment env;

	private String domain = "";
	
	private TrackerClient trackerClient;
	
	private TrackerServer trackerServer;

	private StorageServer storageServer;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Properties properties = new Properties();
		try {
			if (StringUtils.hasLength(env.getProperty("fastdfs.tracker_servers"))) {
				properties.setProperty("fastdfs.http_secret_key", env.getProperty("fastdfs.http_secret_key"));
				properties.setProperty("fastdfs.tracker_servers", env.getProperty("fastdfs.tracker_servers"));
				properties.setProperty("fastdfs.connect_timeout_in_seconds",
						env.getProperty("fastdfs.connect_timeout_in_seconds"));
				properties.setProperty("fastdfs.network_timeout_in_seconds",
						env.getProperty("fastdfs.network_timeout_in_seconds"));
				properties.setProperty("fastdfs.charset", env.getProperty("fastdfs.charset"));
				properties.setProperty("fastdfs.http_anti_steal_token",
						env.getProperty("fastdfs.http_anti_steal_token"));
				properties.setProperty("fastdfs.http_tracker_http_port",
						env.getProperty("fastdfs.http_tracker_http_port"));
				ClientGlobal.initByProperties(properties);
				if (env.getProperty("file.action","").equals("fastdfs")) {
					trackerClient = new TrackerClient();
					trackerServer = trackerClient.getConnection();
				}
				
			}
		} catch (IOException | MyException e) {
			e.printStackTrace();
		}
		domain = env.getProperty("fastdfs.domain");

	}

	@Override
	public String getLocationName() {
		return "fastdfs";
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
	public String getUrl(FileInformation fileInformation) {
		return domain + fileInformation.getUrl();
	}

	@Override
	public String getUrl(FileInformation fileInformation, String pix) {
		return domain + fileInformation.getUrl() + "?w=" + pix + "&h=" + pix;
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