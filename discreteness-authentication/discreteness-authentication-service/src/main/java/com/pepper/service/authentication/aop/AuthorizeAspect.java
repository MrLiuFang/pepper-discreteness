package com.pepper.service.authentication.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pepper.common.emuns.Scope;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.core.exception.AuthorizeException;
import com.pepper.core.exception.NoPermissionException;
import com.pepper.service.authentication.AppAuthorize;
import com.pepper.service.authentication.ConsoleAuthorize;
import com.pepper.service.authentication.IAuthorize;
import com.pepper.service.authentication.PcAuthorize;
import com.pepper.service.authentication.WeixinAuthorize;
import com.pepper.service.redis.string.serializer.SetOperationsService;
import com.pepper.service.redis.string.serializer.StringRedisTemplateService;
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

	@Reference
	private ConsoleAuthorize consoleAuthorize;

	@Reference
	private PcAuthorize pcAuthorize;

	@Reference
	private AppAuthorize appAuthorize;

	@Reference
	private WeixinAuthorize weixinAuthorize;

	@Reference
	private ValueOperationsService valueOperationsService;

	@Reference
	private SetOperationsService setOperationsService;

	@Reference
	private StringRedisTemplateService stringRedisTemplateService;


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
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		/**
		 * 获取注解
		 */
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		Authorize authorizeAop = method.getAnnotation(Authorize.class);

		/**
		 * 获取注解属性
		 */
		boolean authorizeLogin = authorizeAop.authorizeLogin();
		boolean authorizeResources = authorizeAop.authorizeResources();

		/**
		 * 根据scope指定的端类型使用不同的token名从http请求头以及cookie中获取会话码
		 */
		String token = LoginTokenUtil.getLoginToken(GlobalConstant.AUTHORIZE_TOKEN);
		if(!StringUtils.hasText(token)){
			throw new AuthorizeException("登录超时!请重新登录!");
		}
		// 获取登录登录用户的ID
		IAuthorize authorize = getAuthorize(token);
		String userId = authorize.getUserId(token);
		String resourceKey = authorize.getResourceKey(userId);
		// 鉴权用户是否登录
		if (authorizeAop != null && authorizeLogin) {
			// 获取不到用户ID则登录会话超时
			if (!StringUtils.hasText(userId)) {
				throw new AuthorizeException("登录超时!请重新登录!");
			} else {
				// 重新设置登录会话时长
				authorize.setAuthorizeInfo(userId, token);
			}
		}
		// 鉴权url资源是否可调用
		if (authorizeAop != null && authorizeResources) {
			if (StringUtils.hasText(userId)) {
				String url = request.getRequestURI();
				url = url.replaceAll(request.getContextPath(), "");
				if (StringUtils.hasText(url)) {
					// 判断Url资源是否可调用
					if (!setOperationsService.isMember(resourceKey, url)) {
						throw new NoPermissionException("权限不足,请与系统管理员联系!");
					}
				}
			} else {
				throw new AuthorizeException("登录超时!请重新登录!");
			}
		}
		// 执行方法体
		return jp.proceed();

	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	private IAuthorize getAuthorize(String token){
		String value = valueOperationsService.get(token+GlobalConstant.AUTHORIZE_TOKEN_SCOPE);
		Scope scope =  Scope.CONSOLE;
		if(StringUtils.hasText(value)){
			scope = Scope.valueOf(value);
		}else{
			throw new AuthorizeException("登录超时!请重新登录!");
		}
		if(scope.equals(Scope.CONSOLE)){
			return consoleAuthorize;
		}else if(scope.equals(Scope.PC)){
			return pcAuthorize;
		}else if(scope.equals(Scope.APP)){
			return appAuthorize;
		}else if(scope.equals(Scope.WEIXIN)){
			return weixinAuthorize;
		}else{
			return consoleAuthorize;
		}
		
	}
	
}
