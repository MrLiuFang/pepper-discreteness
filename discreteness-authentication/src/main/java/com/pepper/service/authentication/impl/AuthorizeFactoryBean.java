package com.pepper.service.authentication.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pepper.common.emuns.Scope;
import com.pepper.service.authentication.AppAuthorize;
import com.pepper.service.authentication.AuthorizeFactory;
import com.pepper.service.authentication.ConsoleAuthorize;
import com.pepper.service.authentication.FrontAuthorize;
import com.pepper.service.authentication.IAuthorize;
import com.pepper.service.authentication.WeixinAuthorize;

/**
 * 
 * @author mrliu
 *
 */
@Component
public class AuthorizeFactoryBean implements AuthorizeFactory, ApplicationListener<ContextRefreshedEvent> {

	@Resource
	private ConsoleAuthorize consoleAuthorize;

	@Resource
	private FrontAuthorize frontAuthorize;

	@Resource
	private AppAuthorize appAuthorize;

	@Resource
	private WeixinAuthorize weixinAuthorize;
	
	private static Map<String, IAuthorize> map = new HashMap<String, IAuthorize>();

	@Override
	public IAuthorize getAuthorize(String scope) {
		if(map.containsKey(scope)){
			return map.get(scope);
		}
		return null;
	}

	@Override
	public void setAuthorize(String scope, IAuthorize authorize) {
		map.put(scope, authorize);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		map.put(Scope.CONSOLE.toString(), consoleAuthorize);
		map.put(Scope.FRONT.toString(), frontAuthorize);
		map.put(Scope.APP.toString(), appAuthorize);
		map.put(Scope.WEIXIN.toString(), weixinAuthorize);
	}
	
	
}
