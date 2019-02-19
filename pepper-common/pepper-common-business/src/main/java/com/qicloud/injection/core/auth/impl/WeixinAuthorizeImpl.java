package com.pepper.business.core.auth.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import com.pepper.api.redis.ListOperationsService;
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.SetOperationsService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.common.emuns.model.Scope;
import com.pepper.core.GlobalParameter;
import com.pepper.business.api.core.auth.WeixinAuthorize;

/**
 * 微信鉴权类
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = WeixinAuthorize.class)
public class WeixinAuthorizeImpl extends AuthorizeImpl implements WeixinAuthorize {

	private final String _tokenKey = "_weixin_token_user_id";

	@Override
	public String getTokenKey() {
		return _tokenKey;
	}

	@Override
	public Long getSessionTimeOut() {
		return 60L * 60L * 24L * 60L;
	}
	
	@Override
	public String getAuthorizeCookieName() {
		return GlobalParameter.WEIXIN_LOGIN_TOKEN;
	}
	
	@Override
	public Scope getScope() {
		return Scope.WEIXIN;
	}


}
