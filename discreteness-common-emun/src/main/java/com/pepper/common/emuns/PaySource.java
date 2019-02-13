package com.pepper.common.emuns;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

/**
 * 支付来源
 *
 * @author mrliu
 *
 */
public enum PaySource implements IEnum {
	PC(0, "PC网站"), MOBILE_WEBSITE(1, "手机网站"), WX(2, "微信"), IOS(3, "ios"), ANDROID(4, "android");

	private final int key;

	private final String desc;

	private PaySource(int key, String desc) {
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
