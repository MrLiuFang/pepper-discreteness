package com.pepper.business.core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.pepper.core.BaseController;
import com.pepper.core.exception.BusinessException;
import com.pepper.business.api.core.auth.Authorize;
import com.pepper.utils.MapToBeanUtil;
import com.pepper.utils.Util;
import com.pepper.utils.UtilFather;

/**
 *
 * @author liufang
 *
 * @param <T>
 */
public abstract class BaseControllerImpl extends UtilFather implements BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@Override
	public String getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 获取当前登录用户
	 *
	 * @param authorize
	 * @param request
	 * @param target
	 * @return
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCurrentUser(Authorize authorize, Class<T> target) {
		if (authorize == null) {
			return null;
		}
		String token = getCookie(authorize.getAuthorizeCookieName());
		if (!authorize.isLogin(token)) {
			return null;
		}
		Object object = null;
		try {
			/**
			 * 由于获取的map的key和数据库的字段名是一致的（带有“_”），但是映射bean的操作必须map的key和bean的字段名一致，
			 * 所以将带有“_”的key名先转成驼峰标识的。
			 */
			Map<String, Object> userMap = authorize.getCurrentUser(token);
			if (target.getName().equals(Map.class.getName()) || target.getName().equals(HashMap.class.getName())) {
				return (T) userMap;
			}
			Map<String, Object> newUserMap = new HashMap<String, Object>();
			if (Util.isNotEmpty(userMap)) {
				for (Entry<String, Object> en : userMap.entrySet()) {
					if (Util.isNotEmpty(en)) {
						String[] keySplits = en.getKey().split("_");
						if (keySplits.length > 0) {
							StringBuffer newKey = new StringBuffer("");
							for (int i = 0; i < keySplits.length; i++) {
								if (i != 0) {
									newKey.append(Util.upperCaseFirstChar(keySplits[i]));
								} else {
									newKey.append(keySplits[i]);
								}
							}
							newUserMap.put(newKey.toString(), en.getValue());
						} else {
							newUserMap.put(en.getKey(), en.getValue());
						}
					}
				}
			}
			/**
			 * map映射bean
			 */
			object = target.newInstance();
			if (Util.isNotEmpty(object)) {
				MapToBeanUtil.convert(object, newUserMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		if (Util.isNotEmpty(object)) {
			return (T) object;
		}
		return null;
	}

}
