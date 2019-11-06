package com.pepper.common.emuns;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pepper.core.IEnum;

/**
 * 性别公共枚举
 *
 * @author Mr.Liu
 *
 */
public enum Gender implements IEnum {
	FEMALE(0, "女"), MALE(1, "男");

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
