package com.pepper.common.emuns;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

/**
 * 状态枚举
 *
 * @author Mr.Liu
 *
 */
public enum Status implements IEnum {
	DISABLE(0, "禁用"), NORMAL(1, "正常");

	private final int key;

	private final String desc;

	private Status(int key, String desc) {
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
