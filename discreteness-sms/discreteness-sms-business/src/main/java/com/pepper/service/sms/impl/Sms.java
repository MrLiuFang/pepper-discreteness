package com.pepper.service.sms.impl;

import java.util.Date;

/**
 * 短信
 * 
 * @author wwcong
 *
 */
public interface Sms {

	/**
	 *
	 * @return 内容
	 */
	String getContent();

	/**
	 *
	 * @return 手机号码清单
	 */
	String[] getMobilePhones();

	/**
	 *
	 * @return 发送时间
	 */
	Date getSendTime();

	/**
	 *
	 * @return 接收时间
	 */
	Date getReceiveTime();
}
