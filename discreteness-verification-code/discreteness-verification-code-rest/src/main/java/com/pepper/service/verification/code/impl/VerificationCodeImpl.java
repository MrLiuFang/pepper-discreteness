package com.pepper.service.verification.code.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.redis.string.serializer.ValueOperationsService;
import com.pepper.service.verification.code.VerificationCodeService;

/**
 * 
 * @author mrliu
 *
 */
@Service
public class VerificationCodeImpl implements VerificationCodeService {
	
	@Autowired
	private HttpServletRequest request;

	@Reference
	private ValueOperationsService valueOperationsService;

	@Override
	public Boolean validateVerificationCode(String code) {
		Cookie[] cookies =  request.getCookies();
		if(cookies==null || cookies.length <=0 ){
			return false;
		}
		String uuid = null;
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(GlobalConstant.VERIFICATION_CODE_COOKIE)){
				uuid = cookie.getValue();
			}
		}
		if(!StringUtils.hasText(uuid)){
			return false;
		}
		String redisCode = valueOperationsService.get(uuid);
		if(StringUtils.hasText(redisCode)){
			if(code.equals(redisCode)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
}
