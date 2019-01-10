package com.pepper.business.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pepper.core.exception.ExceptionData;

/**
 * 统一异常处理
 *
 * @author mrliu
 *
 */
@Aspect
@Component
@Order(1)
public class ExceptionAspect {
	private static Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

	@Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object around(ProceedingJoinPoint pjp) {
		Object invokeResult = null;
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		try {
			invokeResult = pjp.proceed();
		} catch (Throwable e) {
			ExceptionData exceptionData = new ExceptionData();
			Object o = exceptionData.getResultData(e, method.getReturnType());
			//e.printStackTrace();
			logger.error("Catch a controller exception.", e);
			return o;
		}
		return invokeResult;
	}

}
