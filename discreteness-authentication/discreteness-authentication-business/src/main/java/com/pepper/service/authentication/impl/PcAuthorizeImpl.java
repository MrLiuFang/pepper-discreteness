package com.pepper.service.authentication.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.PcAuthorize;

/**
 * pc后台管理鉴权类
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = PcAuthorize.class)
public class PcAuthorizeImpl extends AuthorizeImpl implements PcAuthorize {

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
		return Scope.PC;
	}
}
