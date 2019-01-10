package com.pepper.service.pay.wx;

import java.util.Map;

/**
 * 微信支付
 * @author mrliu
 *
 */
public interface WxPayService {
	
	/**
	 * @param ip	客户端IP
	 * @param openId
	 * @param orderName
	 * @param orderId
	 * @param amountPayable
	 * @param certData	cert.p12 支付密钥二进制流
	 * @return jsApiConfig
	 */
	public Map<String,String> pay(String ip,String openId, String orderName, String orderId,Integer amountPayable,byte[] certData);
	
	/**
	 * 
	 * @param notifyData
	 * @return
	 */
	public Boolean payNotify(String notifyData);
	
	/**
	 * 获取密钥二进制流
	 * @return
	 */
	public byte[] getCertData();

}
