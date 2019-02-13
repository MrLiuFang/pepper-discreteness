package com.pepper.common.emuns;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

/**
 *
 * @author Mr.Liu
 *
 */
public enum YesOrNo implements IEnum{
	NO(0, "否"), YES(1, "是");

	private final int key;

	private final String desc;

	private YesOrNo(int key, String desc) {
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
