package com.pepper.service.redis.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public abstract class ValueOperationsImpl<K, V> extends Template<K,V> implements ValueOperations<K, V> {

	@Override
	public void set(K key, V value) {
		getTemplate().opsForValue().set(key, value);
	}

	@Override
	public void set(K key, V value, long timeout, TimeUnit unit) {
		getTemplate().opsForValue().set(key, value, timeout, unit);
	}

	@Override
	public Boolean setIfAbsent(K key, V value) {
		return getTemplate().opsForValue().setIfAbsent(key, value);
	}

	@Override
	public void multiSet(Map<? extends K, ? extends V> map) {
		getTemplate().opsForValue().multiSet(map);
	}

	@Override
	public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
		return getTemplate().opsForValue().multiSetIfAbsent(map);
	}

	@Override
	public V get(Object key) {
		V str = getTemplate().opsForValue().get(key);
		return str;
	}

	@Override
	public V getAndSet(K key, V value) {
		return getTemplate().opsForValue().getAndSet(key, value);
	}

	@Override
	public List<V> multiGet(Collection<K> keys) {
		return getTemplate().opsForValue().multiGet(keys);
	}

	@Override
	public Long increment(K key, long delta) {
		return getTemplate().opsForValue().increment(key, delta);
	}

	@Override
	public Double increment(K key, double delta) {
		return getTemplate().opsForValue().increment(key, delta);
	}

	@Override
	public Integer append(K key, String value) {
		return getTemplate().opsForValue().append(key, value);
	}

	@Override
	public String get(K key, long start, long end) {
		return getTemplate().opsForValue().get(key, start, end);
	}

	@Override
	public void set(K key, V value, long offset) {
		getTemplate().opsForValue().set(key, value, offset);
	}

	@Override
	public Long size(K key) {
		return getTemplate().opsForValue().size(key);
	}

	@Override
	public Boolean setBit(K key, long offset, boolean value) {
		return getTemplate().opsForValue().setBit(key, offset, value);
	}

	@Override
	public Boolean getBit(K key, long offset) {
		return getTemplate().opsForValue().getBit(key, offset);
	}

	@Override
	public RedisOperations<K, V> getOperations() {
		return getTemplate().opsForValue().getOperations();
	}

	@Override
	public Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit) {
		return getTemplate().opsForValue().setIfAbsent(key, value, timeout, unit);
	}

	@Override
	public Boolean setIfPresent(K key, V value) {
		return getTemplate().opsForValue().setIfAbsent(key, value);
	}

	@Override
	public Boolean setIfPresent(K key, V value, long timeout, TimeUnit unit) {
		return getTemplate().opsForValue().setIfPresent(key, value, timeout, unit);
	}

	@Override
	public Long increment(K key) {
		return getTemplate().opsForValue().increment(key);
	}

	@Override
	public Long decrement(K key) {
		return getTemplate().opsForValue().decrement(key);
	}

	@Override
	public Long decrement(K key, long delta) {
		return getTemplate().opsForValue().decrement(key, delta);
	}

	@Override
	public List<Long> bitField(K key, BitFieldSubCommands subCommands) {
		return getTemplate().opsForValue().bitField(key, subCommands);
	}

	@Override
	protected RedisOperations<K, V> getTemplate() {
		return getTemplate();
	}
}
