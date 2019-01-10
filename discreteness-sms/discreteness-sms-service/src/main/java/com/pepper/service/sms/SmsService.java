package com.pepper.service.sms;

/**
 * 发送短信
 * @author mrliu
 *
 */
public interface SmsService {

	/**
	 * 发送短信
	 * @param content
	 * @param mobilePhone
	 */
	public void sendMsg(String content,  String... mobilePhone );
}
