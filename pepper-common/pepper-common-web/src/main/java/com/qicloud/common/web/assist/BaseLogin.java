package com.pepper.common.web.assist;

import java.util.List;

import com.pepper.business.api.core.auth.Authorize;

/**
 * 所有端登录相关的conntroller都需要实现login和logout方法
 * 
 * @author weber
 *
 */
public interface BaseLogin {

	String login(String userId, List<String> resourceList, Authorize authorize);

	boolean logout(String token, Authorize authorize);

}
