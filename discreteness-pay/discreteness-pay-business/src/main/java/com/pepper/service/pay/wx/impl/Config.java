package com.pepper.service.pay.wx.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.wxpay.sdk.WXPayConfig;
import com.pepper.dao.pay.PayDao;

/**
 * 微信支付配置
 * @author mrliu
 *
 */
@Component
public class Config implements WXPayConfig ,ApplicationListener<ContextRefreshedEvent> {
	
	@Resource
	private PayDao payDao;
	
	private String appID;
	
	private String key;
	
	private String mchID;
	
	private String notifyUrl;
	
	private byte[] certData;
	
	@Autowired
	private Environment env;

	@Override
	public String getAppID() {
		return this.appID;
	}

	@Override
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
      return certBis;
	}
	
	

	public void setCertData(byte[] certData) {
		this.certData = certData;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 60000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 60000;
	}

	@Override
	public String getKey() {
		
		return this.key;
	}

	@Override
	public String getMchID() {
		
		return this.mchID;
	}
	
	

	public String getNotifyUrl() {
		return notifyUrl;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		this.appID = env.getProperty("weixin_appid")==null?null:env.getProperty("weixin_appid").trim();
		this.key = env.getProperty("weixin_pay_key")==null?null:env.getProperty("weixin_pay_key").trim();
		this.mchID = env.getProperty("weixin_pay_mch_id")==null?null:env.getProperty("weixin_pay_mch_id").trim();
		this.notifyUrl =env.getProperty("weixin_pay_notify_url")==null?null:env.getProperty("weixin_pay_notify_url").trim();
	}

}
