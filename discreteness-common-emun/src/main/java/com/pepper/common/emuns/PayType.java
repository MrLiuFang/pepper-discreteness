package com.pepper.common.emuns;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

/**
 * 支付方式
 *
 * @author mrliu
 *
 */
public enum PayType implements IEnum {

	WX(0, "微信"), ALIPAY(1, "支付宝"), ALLINPAY(2, "通联"), UNIONPAY(3, "银联");

	private final int key;

	private final String desc;

	private PayType(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public Integer getKey() {
		return key;
	}

	@Override
	public String getName() {
		return this.toString();
	}
	
	@Override
	@JsonValue
	public String getDesc(){
		return desc;
	}
}
