package com.pepper.service.redis.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;

/**
 * @author pell
 *
 * @param <H>
 * @param <HK>
 * @param <HV>
 */
public abstract class HashOperationsImpl<H, HK, HV> extends Template<H,HV> implements HashOperations<H, HK, HV>{

	@Override
	public Long delete(H key, Object... hashKeys) {
		return getTemplate().opsForHash().delete(key, hashKeys);
	}

	@Override
	public Boolean hasKey(H key, Object hashKey) {
		return getTemplate().opsForHash().hasKey(key, hashKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HV get(H key, Object hashKey) {
		return (HV) getTemplate().opsForHash().get(key, hashKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HV> multiGet(H key, Collection<HK> hashKeys) {
		return (List<HV>) getTemplate().opsForHash().multiGet(key, (Collection<Object>) hashKeys);
	}

	@Override
	public Long increment(H key, HK hashKey, long delta) {
		return getTemplate().opsForHash().increment(key, hashKey, delta);
	}

	@Override
	public Double increment(H key, HK hashKey, double delta) {
		return getTemplate().opsForHash().increment(key, hashKey, delta);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<HK> keys(H key) {
		return (Set<HK>) getTemplate().opsForHash().keys(key);
	}

	@Override
	public Long size(H key) {
		return getTemplate().opsForHash().size(key);
	}

	@Override
	public void putAll(H key, Map<? extends HK, ? extends HV> m) {
		getTemplate().opsForHash().putAll(key, m);
	}

	@Override
	public void put(H key, HK hashKey, HV value) {
		getTemplate().opsForHash().put(key, hashKey, value);
	}

	@Override
	public Boolean putIfAbsent(H key, HK hashKey, HV value) {
		return getTemplate().opsForHash().putIfAbsent(key, hashKey, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HV> values(H key) {
		return (List<HV>) getTemplate().opsForHash().values(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<HK, HV> entries(H key) {
		return (Map<HK, HV>) getTemplate().opsForHash().entries(key);
	}

	@Override
	public Cursor<Entry<HK, HV>> scan(H key, ScanOptions options) {
		// TODO Auto-generated method stub 类型转换失败!暂不实现
		return null;
	}

	@Override
	public RedisOperations<H, ?> getOperations() {
		return getTemplate();
	}

	@Override
	public Long lengthOfValue(H arg0, HK arg1) {
		return getTemplate().opsForHash().lengthOfValue(arg0, arg1);
	}
	
}
