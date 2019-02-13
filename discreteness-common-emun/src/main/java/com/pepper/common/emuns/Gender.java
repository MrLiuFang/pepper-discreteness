package com.pepper.common.emuns;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

/**
 * 性别公共枚举
 *
 * @author Mr.Liu
 *
 */
public enum Gender implements IEnum {
	FEMALE(0, "女性"), MALE(1, "男性");

	private final int key;

	private final String desc;

	private Gender(int key, String desc) {
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
