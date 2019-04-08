package com.pepper.service.authentication.impl;


import javax.annotation.Resource;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.AppAuthorize;
import com.pepper.service.authentication.AuthorizeFactory;

/**
 * App端鉴权处理类
 *
 * @author mrliu
 *
 */
@Component
public class AppAuthorizeImpl extends AuthorizeImpl implements AppAuthorize {
	
	@Resource
	private AuthorizeFactory authorizeFactory;

	@Override
	public String getTokenKey() {
		return GlobalConstant.APP_TOKEN_USER_ID;
	}

	@Override
	public Long getSessionTimeOut() {
		return Long.valueOf(environment.getProperty(GlobalConstant.APP_SESSION_TIME_OUT.toLowerCase(), String.valueOf((60L * 60L * 24L * 60))));
	}

	@Override
	public Scope getScope() {
		return Scope.APP;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		authorizeFactory.setAuthorize(Scope.APP.toString(), this);
	}



}
