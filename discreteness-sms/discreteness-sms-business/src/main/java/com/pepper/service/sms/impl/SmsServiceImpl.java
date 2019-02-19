package com.pepper.service.sms.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import com.pepper.service.sms.SmsService;

@Service(interfaceClass = SmsService.class)
public class SmsServiceImpl implements SmsService {

	@Resource
	private SmsContentSender smsContentSender;
	
	@Override
	public void sendMsg(String content, String... mobilePhone) {
		try {
			smsContentSender.send(content, mobilePhone);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
