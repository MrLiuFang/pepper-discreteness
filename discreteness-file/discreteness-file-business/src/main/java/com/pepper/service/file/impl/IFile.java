package com.pepper.service.file.impl;

import java.io.File;
import java.io.InputStream;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public interface IFile extends ApplicationListener<ContextRefreshedEvent>  {

	/**
	 * 储存器名称
	 * 
	 * @return
	 */
	public String getStorageTypeName();

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 */
	public String add(File file);

	/**
	 * 取文件
	 * 
	 * @param id
	 * @return
	 */
	public File getFile(String key);

	/**
	 * 取文件（以字节流返回）
	 * 
	 * @param id
	 * @return
	 */
	public InputStream getFileInputStream(String key);

	/**
	 * 取文件连接
	 * 
	 * @param id
	 * @return
	 */
	public String getUrl(com.pepper.model.file.File file);

	/**
	 * 取图片指定大小的连接 TODO 需要优化
	 * 
	 * @param key
	 * @param pix
	 * @return
	 */
	public String getUrl(com.pepper.model.file.File file, String pix);

}
