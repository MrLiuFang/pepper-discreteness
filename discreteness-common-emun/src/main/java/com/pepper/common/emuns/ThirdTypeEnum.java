package com.pepper.common.emuns;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

/**
 * 第三方账号类型
 *
 */
public enum ThirdTypeEnum implements IEnum{

	WeiXin(0, "微信"), Weibo(1, "微博");

	private final int key;

	private final String desc;

	private ThirdTypeEnum(int key, String desc) {
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
