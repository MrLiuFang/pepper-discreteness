package com.pepper.service.pay;

/**
 * 用于支付完成后的业务逻辑处理（其它项目/模块需要实现该接口--DUBBO实现）
 * @author mrliu
 *
 */
public interface PayCallback {
	
	/**
	 * 
	 * @param payAmount
	 * @param orderId
	 * @return
	 */
	public Boolean payCallback(String payAmount,String orderId);

}
