package com.pepper.service.authentication.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.ConsoleAuthorize;

/**
 * pc后台管理鉴权类
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = ConsoleAuthorize.class)
public class ConsoleAuthorizeImpl extends AuthorizeImpl implements ConsoleAuthorize {

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
}
