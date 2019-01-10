package com.pepper.business.aop;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pepper.api.redis.StringRedisTemplateService;
import com.pepper.core.TraceContext;

/**
 * 将事物扩散到Controller层控制，对所有调用dubbo service 的事物进行统一提交/回滚。强制数据的原子性
 * @author mrliu
 *
 */
@Aspect
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class ControllerTransactionalAspect {

	private static Logger logger = LoggerFactory.getLogger(AuthorizeAspect.class.getName());
	
	@Reference
	private StringRedisTemplateService stringRedisTemplateService;

	@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void pointcut() {
	}
	
	/**
	 * 回滚事物
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "pointcut()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
		String destination = TraceContext.getTraceId();
//		stringRedisTemplateService.convertAndSend(destination, "rollback");
//		setKeyExpire(TraceContext.getTraceId());
	}

	@After(value = "pointcut()")
	public void after() {
	}

	/**
	 * 提交事物
	 */
	@AfterReturning(pointcut = "pointcut()")
	public void afterReturning() {
		String destination = TraceContext.getTraceId();
//		stringRedisTemplateService.convertAndSend(destination, "commit");
//		setKeyExpire(TraceContext.getTraceId());
	}
	
	/**
	 * 设置队列Key过期时间
	 * @param key
	 */
	private void setKeyExpire(String key){
		stringRedisTemplateService.expire(key, 10, TimeUnit.SECONDS);
	}
}
