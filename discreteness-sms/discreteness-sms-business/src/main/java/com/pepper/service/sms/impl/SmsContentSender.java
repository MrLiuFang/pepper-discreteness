package com.pepper.service.sms.impl;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

/**
 * 短信内容发送器
 * 
 * @author wwcong
 *
 */
public interface SmsContentSender {
	/**
	 * 同步发送短信
	 *
	 * @param content
	 *            短信内容
	 * @param params
	 *            参数
	 * @param mobilePhones
	 *            手机号码清单
	 * @return 短信发送结果
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	void send(String content,  String... mobilePhones) throws ClientProtocolException, IOException;

	/**
	 * 异步发送短信
	 *
	 * @param content
	 *            短信内容
	 * @param params
	 *            参数
	 * @param mobilePhones
	 *            手机号码清单
	 * @param callback
	 *            短信发送回调
	 */
	void send(String content,  String[] mobilePhones, SmsSendCallback callback);
}
