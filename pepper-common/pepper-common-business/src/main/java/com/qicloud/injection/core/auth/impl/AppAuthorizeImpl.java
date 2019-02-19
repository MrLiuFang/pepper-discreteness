package com.pepper.business.core.auth.impl;


import org.apache.dubbo.config.annotation.Service;
import com.pepper.common.emuns.model.Scope;
import com.pepper.core.GlobalParameter;
import com.pepper.business.api.core.auth.AppAuthorize;

/**
 * App端鉴权处理类
 *
 * @author mrliu
 *
 */
@Service(interfaceClass = AppAuthorize.class)
public class AppAuthorizeImpl extends AuthorizeImpl implements AppAuthorize {


	private final String _tokenKey = "_app_token_user_id";


	@Override
	public String getTokenKey() {
		return _tokenKey;
	}

	@Override
	public Long getSessionTimeOut() {
		return 60L * 60L * 24L * 60;
	}

	@Override
	public String getAuthorizeCookieName() {
		return GlobalParameter.APP_LOGIN_TOKEN;
	}

	@Override
	public Scope getScope() {
		return Scope.MOBILE;
	}

}
