package com.pepper.jstl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.pepper.core.GlobalParameter;
import com.pepper.core.constant.RedisConstant;
import com.pepper.business.core.auth.impl.AdminAuthorizeImpl;
import com.pepper.business.spring.SpringContextUtil;
import com.pepper.business.utils.RedisUtil;
import com.pepper.utils.Util;

/**
 * 权限控制标签（判断无指定权限）
 * 
 * @author Mr.Liu
 *
 */
public class AuthorizeNoTag extends SimpleTagSupport {

	/**
	 * 
	 */
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
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
		String token = Util.getCookieValue(request, GlobalParameter.ADMIN_LOGIN_TOKEN);
		if (Util.isEmpty(token)) {
			return;
		} else {
			RedisUtil redisUtil = (RedisUtil) SpringContextUtil.getBean("redisUtil");
			String userId = redisUtil.getValueOperationsService().get(token + AdminAuthorizeImpl._tokenKey);
			if (Util.isNotEmpty(userId)) {
				boolean exist = redisUtil.getSetOperationsService().isMember(RedisConstant.USER_RESOURCE_CODE + userId,
						code);
				if (!exist) {
					getJspBody().invoke(null);
				}
			}
		}

	}

}
