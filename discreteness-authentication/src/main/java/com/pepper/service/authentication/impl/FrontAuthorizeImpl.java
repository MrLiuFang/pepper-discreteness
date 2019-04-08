package com.pepper.service.authentication.impl;

import javax.annotation.Resource;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.AuthorizeFactory;
import com.pepper.service.authentication.FrontAuthorize;

/**
 * pc前端管理鉴权类
 * 
 * @author mrliu
 *
 */
@Component
public class FrontAuthorizeImpl extends AuthorizeImpl implements FrontAuthorize {

	@Resource
	private AuthorizeFactory authorizeFactory;
	
	@Override
	public String getTokenKey() {
		return GlobalConstant.PC_TOKEN_USER_ID;
	}

	@Override
	public Long getSessionTimeOut() {
		return Long.valueOf(environment.getProperty(GlobalConstant.FRONT_SESSION_TIME_OUT.toLowerCase(), String.valueOf((60L * 30L))));
	}

	@Override
	public Scope getScope() {
		return Scope.FRONT;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		authorizeFactory.setAuthorize(Scope.FRONT.toString(), this);
	}
}
