package com.pepper.service.authentication.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pepper.core.constant.GlobalConstant;
import com.pepper.core.exception.AuthorizeException;
import com.pepper.core.exception.NoPermissionException;
import com.pepper.service.authentication.IAuthorize;
import com.pepper.service.redis.string.serializer.SetOperationsService;
import com.pepper.service.redis.string.serializer.ValueOperationsService;
import com.pepper.util.LoginTokenUtil;

/**
 * pc后台管理登录鉴权
 *
 * @author mrliu
 *
 */
@Aspect
@Component
@Order(2)
public class AuthorizeAspect {

	@Autowired
	private com.pepper.service.authentication.AuthorizeFactory authorizeFactory;

	@Reference
	private ValueOperationsService valueOperationsService;

	@Reference
	private SetOperationsService setOperationsService;



	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AuthorizeAspect.class.getName());

	@Pointcut(value = "@annotation(com.pepper.service.authentication.aop.Authorize)")
	public void pointcut() {
	}

	@Before(value = "pointcut()")
	public void before(JoinPoint joinPoint) {
	}

	@AfterThrowing(pointcut = "pointcut()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
	}

	@After(value = "pointcut()")
	public void after() {
	}

	@AfterReturning(pointcut = "pointcut()")
	public void afterReturning() {
	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		/**
		 * 获取注解
		 */
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		Authorize authorize = method.getAnnotation(Authorize.class);

		/**
		 * 获取注解属性
		 */
		boolean authorizeLogin = authorize.authorizeLogin();
		boolean authorizeResources = authorize.authorizeResources();
		
		if(authorize != null && authorizeResources){
			authorizeResources();
		}else if(authorize != null && authorizeLogin){
			authorizeLogin();
		}
		return jp.proceed();

	}
	/**
	 * 
	 */
	private void authorizeLogin(){
		String token = getToken();
		IAuthorize iAuthorize = getAuthorize(token);
		String userId = iAuthorize.getUserId(token);
		if(!StringUtils.hasText(token)){
			new AuthorizeException("登录超时!请重新登录!");
		}
		// 获取不到用户ID则登录会话超时
		if (!StringUtils.hasText(userId)) {
			new AuthorizeException("登录超时!请重新登录!");
		} else {
			// 重新设置登录会话时长
			iAuthorize.setAuthorizeInfo(userId, token);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private String getToken(){
		/**
		 * 根据scope指定的端类型使用不同的token名从http请求头以及cookie中获取会话码
		 */
		String token = LoginTokenUtil.getLoginToken(GlobalConstant.AUTHORIZE_TOKEN);
		return token;
	}
	
	/**
	 * 
	 */
	private void authorizeResources(){
		authorizeLogin();
		String token = getToken();
		IAuthorize iAuthorize = getAuthorize(token);
		String userId = iAuthorize.getUserId(token);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String resourceKey = iAuthorize.getResourceKey(userId);
		if (StringUtils.hasText(userId)) {
			String url = request.getRequestURI();
			url = url.replaceAll(request.getContextPath(), "");
			if (StringUtils.hasText(url)) {
				// 判断Url资源是否可调用
				if (!setOperationsService.isMember(resourceKey, url)) {
					new NoPermissionException("权限不足,请与系统管理员联系!");
				}
			}
		} else {
			new AuthorizeException("登录超时!请重新登录!");
		}
	}
	
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	private IAuthorize getAuthorize(String token){
		String scope = valueOperationsService.get(token+GlobalConstant.AUTHORIZE_TOKEN_SCOPE);
		if(!StringUtils.hasText(scope)){
			new AuthorizeException("登录超时!请重新登录!");
		}
		return this.authorizeFactory.getAuthorize(scope);
	}
	
}
