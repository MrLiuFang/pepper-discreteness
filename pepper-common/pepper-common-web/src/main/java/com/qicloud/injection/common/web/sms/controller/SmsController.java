package com.pepper.business.common.web.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.dubbo.config.annotation.Reference;
import com.pepper.api.sms.SmsService;
import com.pepper.core.ResultData;

@Controller
@RequestMapping(value = "/sms", method = { RequestMethod.POST })
public class SmsController {
	
	@Reference
	private SmsService smsService;
	
	@RequestMapping(value = "/send")
	@ResponseBody
	public Object send(String content, String mobilePhone) {
		smsService.sendMsg(content, mobilePhone);
		return new ResultData();
	}

}
