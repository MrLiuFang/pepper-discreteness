package com.pepper.service.file.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * @author mrliu
 *
 */
@Component
public class FileBeanFactory {

	public static final Map<String,IFile> beanFactory = new HashMap<String,IFile>();
	
	public void setBean(final String key,final IFile ifile){
		beanFactory.put(key, ifile);
	}
	
	public IFile getBean(final String key){
		return beanFactory.get(key);
	}
}
