package com.pepper.business.aop;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * @author mrliu
 *
 */
@Component
public class AopThreadLocal {

	//当前线程，用来跨AOP传递值
	private   ThreadLocal<Map<String,Object>> tl = new ThreadLocal<Map<String,Object>>();
	
	public   void setValue(String key,Object value){
		Map<String,Object> map = get();
		if(map==null){
			map = new HashMap<String,Object>();
		}
		map.put(key, value);
		tl.set(map);
	}
	
	public  Map<String,Object> get(){
		return tl.get();
	}
	
	public void clean(){
		tl.remove();
	}
}
