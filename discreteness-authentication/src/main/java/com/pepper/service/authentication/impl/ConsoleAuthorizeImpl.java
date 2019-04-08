package com.pepper.service.authentication.impl;

import javax.annotation.Resource;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.AuthorizeFactory;
import com.pepper.service.authentication.ConsoleAuthorize;

/**
 * pc后台管理鉴权类
 * 
 * @author mrliu
 *
 */
@Component
public class ConsoleAuthorizeImpl extends AuthorizeImpl implements ConsoleAuthorize {

	@Resource
	private AuthorizeFactory authorizeFactory;
	
	@Override
	public String getTokenKey() {
		return GlobalConstant.PC_ADMIN_TOKEN_USER_ID;
	}

	@Override
	public Long getSessionTimeOut() {
		return Long.valueOf(environment.getProperty(GlobalConstant.PC_ADMIN_SESSION_TIME_OUT.toLowerCase(), String.valueOf((60L * 30L)))) ;
	}

	@Override
	public Scope getScope() {
		return Scope.CONSOLE;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		authorizeFactory.setAuthorize(Scope.CONSOLE.toString(), this);
	}
}
