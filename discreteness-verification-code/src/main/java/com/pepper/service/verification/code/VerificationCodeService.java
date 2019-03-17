package com.pepper.service.verification.code;

/**
 * 
 * @author mrliu
 *
 */
public interface VerificationCodeService {

	/**
	 * 校验验证码
	 * @param code
	 * @return
	 */
	public Boolean validateVerificationCode(String code);
}
