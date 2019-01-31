package com.pepper.service.authentication.impl;


import org.springframework.stereotype.Component;

import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.AppAuthorize;

/**
 * App端鉴权处理类
 *
 * @author mrliu
 *
 */
@Component
public class AppAuthorizeImpl extends AuthorizeImpl implements AppAuthorize {

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



}
