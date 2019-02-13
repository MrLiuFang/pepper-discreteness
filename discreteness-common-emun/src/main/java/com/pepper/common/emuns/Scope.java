package com.pepper.common.emuns;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

public enum Scope implements IEnum {
	FRONT(0, "前端"), CONSOLE(1, "后台"), APP(2, "移动端"), WEIXIN(3, "微信端");

	private final int key;

	private final String desc;

	private Scope(int key, String desc) {
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
