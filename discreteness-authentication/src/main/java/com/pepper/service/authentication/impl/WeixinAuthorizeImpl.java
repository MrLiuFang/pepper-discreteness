package com.pepper.service.authentication.impl;

import javax.annotation.Resource;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.AuthorizeFactory;
import com.pepper.service.authentication.WeixinAuthorize;

/**
 * 微信鉴权类
 * 
 * @author mrliu
 *
 */
@Component
public class WeixinAuthorizeImpl extends AuthorizeImpl implements WeixinAuthorize {

	@Resource
	private AuthorizeFactory authorizeFactory;
	
	@Override
	public String getTokenKey() {
		return GlobalConstant.WEIXIN_TOKEN_USER_ID;
	}

	@Override
	public Long getSessionTimeOut() {
		return Long.valueOf(environment.getProperty(GlobalConstant.WEIXIN_SESSION_TIME_OUT.toLowerCase(), String.valueOf((60L * 60L * 24L * 60L))));
	}
	
	@Override
	public Scope getScope() {
		return Scope.WEIXIN;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		authorizeFactory.setAuthorize(Scope.WEIXIN.toString(), this);
	}

}
