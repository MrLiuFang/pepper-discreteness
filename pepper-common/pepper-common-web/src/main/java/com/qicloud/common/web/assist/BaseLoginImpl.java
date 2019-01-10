package com.pepper.common.web.assist;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;

import com.pepper.business.api.core.auth.Authorize;
import com.pepper.business.core.impl.BaseControllerImpl;

public abstract class BaseLoginImpl extends BaseControllerImpl implements BaseLogin {

	@Override
	public String login(String userId, List<String> resourceList, Authorize authorize) {
		String token = UUID.randomUUID().toString();
		// 记录用户登录状态
		authorize.setAuthorizeInfo(userId, token);
		// 先删除以前的权限资源
		authorize.deleteUserResources(userId);
		// 设置权限资源
		authorize.setUserResources(resourceList, userId);

		/**
		 * 写token cookie到前端
		 */
		Cookie cookieToken = new Cookie(authorize.getAuthorizeCookieName(), token);
		cookieToken.setMaxAge(-1);
		cookieToken.setPath("/");
		response.addCookie(cookieToken);
		return token;

	}

	@Override
	public boolean logout(String token, Authorize authorize) {
		String userId = authorize.getUserId(token);
		authorize.deleteAuthorizeInfo(token);
		authorize.deleteUserResources(userId);
		authorize.deleteResourceCode(userId);
		return true;
	}

}
