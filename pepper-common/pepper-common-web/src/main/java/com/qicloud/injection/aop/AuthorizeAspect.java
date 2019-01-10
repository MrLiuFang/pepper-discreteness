package com.pepper.business.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
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
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.SetOperationsService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.common.emuns.model.Scope;
import com.pepper.core.GlobalParameter;
import com.pepper.core.exception.AuthorizeException;
import com.pepper.core.exception.NoPermissionException;
import com.pepper.business.api.core.auth.AdminAuthorize;
import com.pepper.business.api.core.auth.AppAuthorize;
import com.pepper.business.api.core.auth.PcAuthorize;
import com.pepper.business.api.core.auth.WeixinAuthorize;
import com.pepper.utils.Util;

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
	private AdminAuthorize adminAuthorize;

	@Reference
	private PcAuthorize pcAuthorize;

	@Reference
	private AppAuthorize appAuthorize;

	@Reference
	private WeixinAuthorize weixinAuthorize;

	@Reference
	private ValueOperationsService<String, String> valueOperationsService;

	@Reference
	private SetOperationsService<String, String> setOperationsService;

	@Reference
	private RedisOperationsService<String, String> redisOperationsService;

	@Resource
	private AopThreadLocal aopThreadLocal;

	private static Logger logger = LoggerFactory.getLogger(AuthorizeAspect.class.getName());

	@Pointcut(value = "@annotation(com.pepper.business.aop.AuthorizeAop)")
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
		aopThreadLocal.clean();
		aopThreadLocal.setValue("createDate", new Date());
		/**
		 * 获取注解
		 */
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		AuthorizeAop authorizeAop = method.getAnnotation(AuthorizeAop.class);

		/**
		 * 获取注解属性
		 */
		boolean authorizeLogin = authorizeAop.authorizeLogin();
		boolean authorizeResources = authorizeAop.authorizeResources();
		Scope authorizeScope = authorizeAop.authorizeScope();

		/**
		 * 根据scope指定的端类型使用不同的token名从http请求头以及cookie中获取会话码
		 */
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String token = null;
		String tokenName = null;
		if (Scope.CONSOLE.equals(authorizeScope)) {
			tokenName = GlobalParameter.ADMIN_LOGIN_TOKEN;
		} else if (Scope.FRONT.equals(authorizeScope)) {
			tokenName = GlobalParameter.PC_LOGIN_TOKEN;
		} else if (Scope.MOBILE.equals(authorizeScope)) {
			tokenName = GlobalParameter.APP_LOGIN_TOKEN;
		} else if (Scope.WEIXIN.equals(authorizeScope)) {
			tokenName = GlobalParameter.WEIXIN_LOGIN_TOKEN;
		}
		if (request.getHeader(tokenName) != null) {
			token = request.getHeader(tokenName).toString();
		}
		token = Util.getCookieValue(request, tokenName);

		// 获取参数

		// 获取登录登录用户的ID
		String user_id = null;
		String resourceKey = null;

		if (Scope.CONSOLE.equals(authorizeScope)) {
			user_id = adminAuthorize.getUserId(token);
			resourceKey = adminAuthorize.getResourceKey(user_id);
		} else if (Scope.FRONT.equals(authorizeScope)) {
			user_id = pcAuthorize.getUserId(token);
			resourceKey = pcAuthorize.getResourceKey(user_id);
		} else if (Scope.MOBILE.equals(authorizeScope)) {
			user_id = appAuthorize.getUserId(token);
			resourceKey = appAuthorize.getResourceKey(user_id);
		} else if (Scope.WEIXIN.equals(authorizeScope)) {
			user_id = weixinAuthorize.getUserId(token);
			resourceKey = weixinAuthorize.getResourceKey(user_id);
		}

		// 鉴权用户是否登录
		if (authorizeAop != null && authorizeLogin) {
			// 获取不到用户ID则登录会话超时
			if (!StringUtils.hasText(user_id)) {
				throw new AuthorizeException("登录超时!请重新登录!");
			} else {
				// 重新设置登录会话时长
				adminAuthorize.setAuthorizeInfo(user_id, token);
			}
		}
		// 鉴权url资源是否可调用
		if (authorizeAop != null && authorizeResources) {
			if (StringUtils.hasText(user_id)) {
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

		// 设置值传递到下个AOP（如系统日志）

		aopThreadLocal.setValue("userId", user_id);

		// 执行方法体
		Object object = jp.proceed();
		return object;

	}
}
