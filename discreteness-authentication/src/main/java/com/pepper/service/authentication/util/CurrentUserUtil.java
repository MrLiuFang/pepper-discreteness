package com.pepper.service.authentication.util;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pepper.core.base.ICurrentUser;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.core.exception.AuthorizeException;
import com.pepper.service.authentication.IAuthorize;
import com.pepper.service.authentication.impl.AuthorizeFactoryBean;
import com.pepper.service.redis.string.serializer.ValueOperationsService;
import com.pepper.util.LoginTokenUtil;

@Component
public class CurrentUserUtil implements ICurrentUser {
	
	@Reference
	private ValueOperationsService valueOperationsService;
	
	@Resource
	private AuthorizeFactoryBean authorizeFactoryBean;

	public Object getCurrentUser() {
		String token = LoginTokenUtil.getLoginToken(GlobalConstant.AUTHORIZE_TOKEN);
		if(token==null || !StringUtils.hasText(token.toString())){
			return null;
		}
		String scope = valueOperationsService.get(token + GlobalConstant.AUTHORIZE_TOKEN_SCOPE);
		if (scope==null || !StringUtils.hasText(scope)) {
			return null;
		}
		try {
			IAuthorize iAuthorize = authorizeFactoryBean.getAuthorize(scope);
			Object user = iAuthorize.getCurrentUser(token);
			if(user == null){
				throw new AuthorizeException("登录超时!请重新登录!");
			}
			return user;
		} catch (Exception e) {
			throw new AuthorizeException("登录超时!请重新登录!");
		}
	}
}
