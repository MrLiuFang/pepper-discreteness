package com.pepper.service.authentication.impl;

import org.springframework.stereotype.Component;

import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.FrontAuthorize;

/**
 * pc前端管理鉴权类
 * 
 * @author mrliu
 *
 */
@Component
public class FrontAuthorizeImpl extends AuthorizeImpl implements FrontAuthorize {

	@Override
	public String getTokenKey() {
		return GlobalConstant.PC_TOKEN_USER_ID;
	}

	@Override
	public Long getSessionTimeOut() {
		return Long.valueOf(environment.getProperty(GlobalConstant.PC_SESSION_TIME_OUT.toLowerCase(), String.valueOf((60L * 30L))));
	}

	@Override
	public Scope getScope() {
		return Scope.FRONT;
	}
}
