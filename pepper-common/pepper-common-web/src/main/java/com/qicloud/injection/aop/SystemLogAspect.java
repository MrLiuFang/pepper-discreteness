package com.pepper.business.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pepper.api.system.log.SystemLogService;
import com.pepper.core.ObjectMapperCustomer;
import com.pepper.core.exception.ExceptionData;

/**
 * 系统日志Aspect
 * 
 * @author liufang
 *
 */
@Aspect
@Component
@Order(999)
@Lazy(value = true)
public class SystemLogAspect {
	private static Logger logger = LogManager.getLogger(SystemLogAspect.class.getName());

	private ObjectMapperCustomer objectMapper = new ObjectMapperCustomer();

	@Reference
	private SystemLogService systemLogService;

	@Resource
	private AopThreadLocal aopThreadLocal;

	@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	// @Pointcut("execution(* com.pepper.business.controller.*.*(..))")
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
	public Object around(ProceedingJoinPoint jp) throws JsonProcessingException {
		Map<String, Object> map = aopThreadLocal.get();
		if (map == null) {
			aopThreadLocal.setValue("createDate", new Date());
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// 获取注解
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		NotRecordSystemLog notRecordSystemLog = method.getAnnotation(NotRecordSystemLog.class);

		// 获取参数
		Object[] args = jp.getArgs();
		Object object = null;
		try {
			object = jp.proceed();
		} catch (Throwable e) {
			aopThreadLocal.setValue("exception", e.toString());
			aopThreadLocal.setValue("isException", true);
			e.printStackTrace();
			ExceptionData exceptionData = new ExceptionData();
			object = exceptionData.getResultData(e, method.getReturnType());
			exceptionData = null;
		}
		String inPutParameter = this.objectMapper.writeValueAsString(request.getParameterMap());
		String outPutParameter = this.objectMapper.writeValueAsString(object);
		String url = request.getRequestURI();
		String remoteAddress = request.getRemoteAddr();
		saveLog(notRecordSystemLog, inPutParameter, outPutParameter, url, remoteAddress);
		return object;
	}

	/**
	 * 保存日志
	 * 
	 * @param notRecordSystemLog
	 * @param inPutParameter
	 * @param outPutParameter
	 * @param url
	 * @param remote_address
	 */
	private void saveLog(NotRecordSystemLog notRecordSystemLog, String inPutParameter, String outPutParameter,
			String url, String remoteAddress) {
		if (notRecordSystemLog != null) {
			return;
		}
		Map<String, Object> map = aopThreadLocal.get();
		String userId = map.get("userId") == null ? null : map.get("userId").toString();
		String exception = map.get("exception") == null ? null : map.get("exception").toString();
		Boolean isException = map.get("isException") == null ? false : (Boolean) map.get("isException");
		Date createDate = map.get("createDate") == null ? new Date() : (Date) map.get("createDate");
		Long executeTime = new Date().getTime() - createDate.getTime();
		try {
			systemLogService.saveLog(userId, inPutParameter, outPutParameter, exception, isException, url, executeTime,
					remoteAddress, createDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map = null;
		aopThreadLocal.clean();
	}
}
