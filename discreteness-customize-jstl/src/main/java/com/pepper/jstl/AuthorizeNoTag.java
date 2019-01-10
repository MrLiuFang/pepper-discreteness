package com.pepper.jstl;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.util.StringUtils;

import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.redis.util.RedisUtil;
import com.pepper.util.SpringContextUtil;


/**
 * 权限控制标签（判断无指定权限）
 * 
 * @author Mr.Liu
 *
 */
@SuppressWarnings("deprecation")
public class AuthorizeNoTag extends SimpleTagSupport {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2245899599050348820L;

	/**
	 * 菜单/资源编码
	 */
	private String code;

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		String token = getCookie(GlobalConstant.AUTHORIZE_TOKEN);
		if (!StringUtils.hasText(token)) {
			return;
		} else {
			RedisUtil redisUtil = (RedisUtil) SpringContextUtil.getBean("redisUtil");
			String userId = redisUtil.getValueOperationsService().get(token +GlobalConstant.PC_ADMIN_TOKEN_USER_ID);
			if (StringUtils.hasText(userId)) {
				boolean exist = redisUtil.getSetOperationsService().isMember(GlobalConstant.USER_RESOURCE_CODE + userId,code);
				if (!exist) {
					getJspBody().invoke(null);
				}
			}
		}

	}
	
	private String getCookie(String name) {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
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

}
