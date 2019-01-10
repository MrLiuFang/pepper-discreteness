package com.pepper.service.redis.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public abstract class ListOperationsImpl<K, V> extends Template<K,V> implements ListOperations<K, V> {

	@Override
	public List<V> range(K key, long start, long end) {
		return getTemplate().opsForList().range(key, start, end);
	}

	@Override
	public void trim(K key, long start, long end) {
		getTemplate().opsForList().trim(key, start, end);
	}

	@Override
	public Long size(K key) {
		return getTemplate().opsForList().size(key);
	}

	@Override
	public Long leftPush(K key, V value) {
		return getTemplate().opsForList().leftPush(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long leftPushAll(K key, V... values) {
		return getTemplate().opsForList().leftPushAll(key, values);
	}

	@Override
	public Long leftPushAll(K key, Collection<V> values) {
		return getTemplate().opsForList().leftPushAll(key, values);
	}

	@Override
	public Long leftPushIfPresent(K key, V value) {
		return getTemplate().opsForList().leftPushIfPresent(key, value);
	}

	@Override
	public Long leftPush(K key, V pivot, V value) {
		return getTemplate().opsForList().leftPush(key, pivot, value);
	}

	@Override
	public Long rightPush(K key, V value) {
		return getTemplate().opsForList().rightPush(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long rightPushAll(K key, V... values) {
		return getTemplate().opsForList().rightPushAll(key, values);
	}

	@Override
	public Long rightPushAll(K key, Collection<V> values) {
		return getTemplate().opsForList().rightPushAll(key, values);
	}

	@Override
	public Long rightPushIfPresent(K key, V value) {
		return getTemplate().opsForList().rightPushIfPresent(key, value);
	}

	@Override
	public Long rightPush(K key, V pivot, V value) {
		return getTemplate().opsForList().rightPush(key, value);
	}

	@Override
	public void set(K key, long index, V value) {
		getTemplate().opsForList().set(key, index, value);

	}

	@Override
	public Long remove(K key, long count, Object value) {
		return getTemplate().opsForList().remove(key, count, value);
	}

	@Override
	public V index(K key, long index) {
		return getTemplate().opsForList().index(key, index);
	}

	@Override
	public V leftPop(K key) {
		return getTemplate().opsForList().leftPop(key);
	}

	@Override
	public V leftPop(K key, long timeout, TimeUnit unit) {
		return getTemplate().opsForList().leftPop(key, timeout, unit);
	}

	@Override
	public V rightPop(K key) {
		return getTemplate().opsForList().rightPop(key);
	}

	@Override
	public V rightPop(K key, long timeout, TimeUnit unit) {
		return getTemplate().opsForList().rightPop(key, timeout, unit);
	}

	@Override
	public V rightPopAndLeftPush(K sourceKey, K destinationKey) {
		return getTemplate().opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
	}

	@Override
	public V rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit) {
		return getTemplate().opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout,
				unit);
	}

	@Override
	public RedisOperations<K, V> getOperations() {
		return getTemplate();
	}

}
