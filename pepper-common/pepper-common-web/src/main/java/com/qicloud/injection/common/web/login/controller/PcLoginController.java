package com.pepper.business.common.web.login.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pepper.api.common.MemberThirdService;
import com.pepper.common.web.assist.BaseLoginImpl;
import com.pepper.core.BaseController;
import com.pepper.core.MemberCommonService;
import com.pepper.core.ResultData;
import com.pepper.core.exception.AuthorizeException;
import com.pepper.business.aop.AuthorizeAop;
import com.pepper.business.api.core.auth.PcAuthorize;
import com.pepper.model.member.MemberThird;
import com.pepper.utils.Util;

/**
 * PC端鉴权Controller
 * 
 * @author weber
 *
 */
@Controller
@RequestMapping(value = "/pc/user/account", method = { RequestMethod.POST })
public class PcLoginController extends BaseLoginImpl implements BaseController {

	//变异原生DUBBO范型调用，非官方标准版（前提条件需要在部署时LIBS包下面有在这个类。官方标准版的可以没有此类，写法不一样，官方标准版比较复杂）
	@Reference(interfaceName = "com.pepper.api.console.role.RoleService",generic=true)
	private Object roleService;
	
	@Reference
	private MemberThirdService<MemberThird> memberThirdService;

	@Reference
	private PcAuthorize pcAuthorize;

	@Reference
	private MemberCommonService memberCommonService;

	/**
	 * 登录
	 * 
	 * @return
	 * @throws JsonProcessingException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws AuthorizeException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(String account, String pwd)
			throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, AuthorizeException {
		String memberId = memberCommonService.loginMember(account, pwd);
		if (Util.isEmpty(memberId)) {
			throw new AuthorizeException("登录失败!用户名/密码错误!");
		}
		List<String> resourceList = (List<String>) roleService.getClass().getMethod("queryUserAllResources", String.class).invoke(roleService, memberId);
		login(memberId, resourceList, pcAuthorize);
		return new ResultData();
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loginOut")
	@Authorize(authorizeResources = false)
	public Object loginOut() {
		String token = this.getCookie(pcAuthorize.getAuthorizeCookieName());
		logout(token, pcAuthorize);
		return new ResultData();
	}

}
