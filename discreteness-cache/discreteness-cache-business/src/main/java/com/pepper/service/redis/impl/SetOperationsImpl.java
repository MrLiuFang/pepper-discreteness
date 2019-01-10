package com.pepper.service.redis.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public abstract class SetOperationsImpl<K, V> extends Template<K,V> implements SetOperations<K, V> {

	@Override
	public RedisOperations<K, V> getOperations() {
		return getTemplate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long add(K key, V... values) {
		return getTemplate().opsForSet().add(key, values);
	}

	@Override
	public Long remove(K key, Object... values) {
		return getTemplate().opsForSet().remove(key, values);
	}

	@Override
	public V pop(K key) {
		return getTemplate().opsForSet().pop(key);
	}

	@Override
	public List<V> pop(K key, long count) {
		return getTemplate().opsForSet().pop(key, count);
	}

	@Override
	public Boolean move(K key, V value, K destKey) {
		return getTemplate().opsForSet().move(key, value, destKey);
	}

	@Override
	public Long size(K key) {
		return getTemplate().opsForSet().size(key);
	}

	@Override
	public Boolean isMember(K key, Object o) {
		return getTemplate().opsForSet().isMember(key, o);
	}

	@Override
	public Set<V> intersect(K key, K otherKey) {
		return getTemplate().opsForSet().intersect(key, otherKey);
	}

	@Override
	public Set<V> intersect(K key, Collection<K> otherKeys) {
		return getTemplate().opsForSet().intersect(key, otherKeys);
	}

	@Override
	public Long intersectAndStore(K key, K otherKey, K destKey) {
		return getTemplate().opsForSet().intersectAndStore(key, otherKey, destKey);
	}

	@Override
	public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
		return getTemplate().opsForSet().intersectAndStore(key, otherKeys, destKey);
	}

	@Override
	public Set<V> union(K key, K otherKey) {
		return getTemplate().opsForSet().union(key, otherKey);
	}

	@Override
	public Set<V> union(K key, Collection<K> otherKeys) {
		return getTemplate().opsForSet().union(key, otherKeys);
	}

	@Override
	public Long unionAndStore(K key, K otherKey, K destKey) {
		return getTemplate().opsForSet().unionAndStore(key, otherKey, destKey);
	}

	@Override
	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey) {
		return getTemplate().opsForSet().unionAndStore(key, otherKeys, destKey);
	}

	@Override
	public Set<V> difference(K key, K otherKey) {
		return getTemplate().opsForSet().difference(key, otherKey);
	}

	@Override
	public Set<V> difference(K key, Collection<K> otherKeys) {
		return getTemplate().opsForSet().difference(key, otherKeys);
	}

	@Override
	public Long differenceAndStore(K key, K otherKey, K destKey) {
		return getTemplate().opsForSet().differenceAndStore(key, otherKey, destKey);
	}

	@Override
	public Long differenceAndStore(K key, Collection<K> otherKeys, K destKey) {
		return getTemplate().opsForSet().differenceAndStore(key, otherKeys, destKey);
	}

	@Override
	public Set<V> members(K key) {
		return getTemplate().opsForSet().members(key);
	}

	@Override
	public V randomMember(K key) {
		return getTemplate().opsForSet().randomMember(key);
	}

	@Override
	public Set<V> distinctRandomMembers(K key, long count) {
		return getTemplate().opsForSet().distinctRandomMembers(key, count);
	}

	@Override
	public List<V> randomMembers(K key, long count) {
		return getTemplate().opsForSet().randomMembers(key, count);
	}

	@Override
	public Cursor<V> scan(K key, ScanOptions options) {
		return getTemplate().opsForSet().scan(key, options);
	}

}
