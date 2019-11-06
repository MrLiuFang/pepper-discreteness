package com.pepper.common.emuns;

import java.util.HashMap;
import java.util.Map;

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
	
	public String getDesc(){
		return desc;
	}
	
	@JsonValue
	public Map<String, Object> jsonValue() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("desc", desc);
		map.put("name", getName());
		return map;
	}
	
}
