package com.pepper.business.utils.verification;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.apache.dubbo.config.annotation.Reference;
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.core.constant.ParameterConstant;
import com.pepper.core.exception.BusinessException;
import com.pepper.utils.Util;

@Component
public class CodeHelper {

	@Reference
	private ValueOperationsService<String, String> valueOperationsService;

	@Reference
	private RedisOperationsService<String, String> redisOperationsService;

	@Autowired
	private Environment environment;

	private static final int MAX_MINUTES = 60 * 24;

	/**
	 * 创建code
	 *
	 * @param type
	 * @param target
	 * @param minutes
	 * @return
	 * @throws BusinessException
	 */
	public String create(String type, String target, int minutes) throws BusinessException {
		if (minutes <= 0) {
			throw new BusinessException("有效时间必须大于零:" + minutes);
		}
		if (minutes >= MAX_MINUTES) {
			throw new BusinessException("检验码组件有效时间不能超过一天." + minutes);
		}
		String code = this.newCode();
		valueOperationsService.set(getKey(type, target), code, minutes, TimeUnit.MINUTES);
		return code;
	}

	/**
	 * 将创建的验证码存储到redis数据库中
	 *
	 * @param type
	 *            验证码业务类型
	 * @param target
	 *            目标唯一标识，比如用户的手机号
	 * @param minutes
	 *            验证码存储时长
	 * @throws BusinessException
	 */
	public void redis(String type, String target, int minutes, String vCode) throws BusinessException {
		if (minutes <= 0) {
			throw new BusinessException("有效时间必须大于零:" + minutes);
		}
		if (minutes >= MAX_MINUTES) {
			throw new BusinessException("检验码组件有效时间不能超过一天:" + minutes);
		}
		valueOperationsService.set(getKey(type, target), vCode, minutes, TimeUnit.MINUTES);
	}

	/**
	 * 验证code
	 *
	 * @param type
	 * @param target
	 * @param code
	 * @return
	 */
	public boolean verification(String type, String target, String code) {
		String notVerificationVerifyCode = environment.getProperty(ParameterConstant.not_verification_verify_code);
		if(StringUtils.hasText( notVerificationVerifyCode ) && "true".equals(notVerificationVerifyCode.toLowerCase())){
			return true;
		}
		String superVerifyCode = environment.getProperty(ParameterConstant.super_verify_code);
		if (Util.isNotEmpty(superVerifyCode) && superVerifyCode.equals(code)) {
			return true;
		}
		if (redisOperationsService.hasKey(getKey(type, target))) {
			String temp = valueOperationsService.get(getKey(type, target));
			if (code.equalsIgnoreCase(temp)) {
				// 验证成功后清除
				redisOperationsService.delete(getKey(type, target));
				return true;
			}
		}
		return false;
	}

	private String getKey(String type, String target) {
		return "verifiation:code:" + type + "_" + target;
	}

	// 搞出六位验证码
	private String newCode() {
		StringBuffer sb = new StringBuffer();
		for (int index = 0; index < 6; index++) {
			int number = new Random().nextInt(10);
			sb.append(number);
		}
		return sb.toString();
	}

	/**
	 * 随机获取n个字符作为验证码。
	 *
	 * @return
	 */
	public static String getVerifyCodeChars(int n) {
		char[] c = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8' };
		int randNum = -1;
		char[] resc = new char[n];
		for (int i = 0; i < n; i++) {
			randNum = getRandomIntInRang(0, c.length-1);
			try {
				resc[i] = c[randNum];
			} catch (Exception e) {
				resc[i] = 'P';
				continue;
			}
		}
		return new String(resc);
	}

	/**
	 * 获取某个范围的随机数。 如获取0-5的随机数，minRang:0，maxRang:5， 如获取5-50的随机数，minRang:5，maxRang:50
	 *
	 * @param minRang
	 * @param maxRang
	 * @return
	 */
	public static int getRandomIntInRang(int minRang, int maxRang) {
		Random rand = new Random();
		int randNum = rand.nextInt(maxRang + 1) + minRang;
		return randNum;
	}
}
