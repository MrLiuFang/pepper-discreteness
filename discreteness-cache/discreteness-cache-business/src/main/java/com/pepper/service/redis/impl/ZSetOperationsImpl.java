package com.pepper.service.redis.impl;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.connection.RedisZSetCommands.Weights;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public abstract class ZSetOperationsImpl<K, V> extends Template<K,V> implements ZSetOperations<K, V> {

	@Override
	public Boolean add(K key, V value, double score) {
		return getTemplate().opsForZSet().add(key, value, score);
	}

	@Override
	public Long add(K key, Set<TypedTuple<V>> tuples) {
		return getTemplate().opsForZSet().add(key, tuples);
	}

	@Override
	public Long remove(K key, Object... values) {
		return getTemplate().opsForZSet().remove(key, values);
	}

	@Override
	public Double incrementScore(K key, V value, double delta) {
		return getTemplate().opsForZSet().incrementScore(key, value, delta);
	}

	@Override
	public Long rank(K key, Object o) {
		return getTemplate().opsForZSet().rank(key, o);
	}

	@Override
	public Long reverseRank(K key, Object o) {
		return getTemplate().opsForZSet().reverseRank(key, o);
	}

	@Override
	public Set<V> range(K key, long start, long end) {
		return getTemplate().opsForZSet().range(key, start, end);
	}

	@Override
	public Set<TypedTuple<V>> rangeWithScores(K key, long start, long end) {
		return getTemplate().opsForZSet().rangeWithScores(key, start, end);
	}

	@Override
	public Set<V> rangeByScore(K key, double min, double max) {
		return getTemplate().opsForZSet().rangeByScore(key, min, max);
	}

	@Override
	public Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max) {
		return getTemplate().opsForZSet().rangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<V> rangeByScore(K key, double min, double max, long offset, long count) {
		return getTemplate().opsForZSet().rangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count) {
		return getTemplate().opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<V> reverseRange(K key, long start, long end) {
		return getTemplate().opsForZSet().reverseRange(key, start, end);
	}

	@Override
	public Set<TypedTuple<V>> reverseRangeWithScores(K key, long start, long end) {
		return getTemplate().opsForZSet().reverseRangeWithScores(key, start, end);
	}

	@Override
	public Set<V> reverseRangeByScore(K key, double min, double max) {
		return getTemplate().opsForZSet().reverseRangeByScore(key, min, max);
	}

	@Override
	public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max) {
		return getTemplate().opsForZSet().reverseRangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<V> reverseRangeByScore(K key, double min, double max, long offset, long count) {
		return getTemplate().opsForZSet().reverseRangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max, long offset, long count) {
		return getTemplate().opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Long count(K key, double min, double max) {
		return getTemplate().opsForZSet().count(key, min, max);
	}

	@Override
	public Long size(K key) {
		return getTemplate().opsForZSet().size(key);
	}

	@Override
	public Long zCard(K key) {
		return getTemplate().opsForZSet().zCard(key);
	}

	@Override
	public Double score(K key, Object o) {
		return getTemplate().opsForZSet().score(key, o);
	}

	@Override
	public Long removeRange(K key, long start, long end) {
		return getTemplate().opsForZSet().removeRange(key, start, end);
	}

	@Override
	public Long removeRangeByScore(K key, double min, double max) {
		return getTemplate().opsForZSet().removeRangeByScore(key, min, max);
	}

	@Override
	public Long unionAndStore(K key, K otherKey, K destKey) {
		return getTemplate().opsForZSet().unionAndStore(key, otherKey, destKey);
	}

	@Override
	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey) {
		return getTemplate().opsForZSet().unionAndStore(key, otherKeys, destKey);
	}

	@Override
	public Long intersectAndStore(K key, K otherKey, K destKey) {
		return getTemplate().opsForZSet().intersectAndStore(key, otherKey, destKey);
	}

	@Override
	public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
		return getTemplate().opsForZSet().intersectAndStore(key, otherKeys, destKey);
	}

	@Override
	public Cursor<TypedTuple<V>> scan(K key, ScanOptions options) {
		return getTemplate().opsForZSet().scan(key, options);
	}

	@Override
	public Set<V> rangeByLex(K key, Range range) {
		return getTemplate().opsForZSet().rangeByLex(key, range);
	}

	@Override
	public Set<V> rangeByLex(K key, Range range, Limit limit) {
		return getTemplate().opsForZSet().rangeByLex(key, range, limit);
	}

	@Override
	public RedisOperations<K, V> getOperations() {
		return getTemplate();
	}

	@Override
	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey, Aggregate aggregate, Weights weights) {
		return getTemplate().opsForZSet().unionAndStore(key, otherKeys, destKey, aggregate, weights);
	}

	@Override
	public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey, Aggregate aggregate, Weights weights) {
		return getTemplate().opsForZSet().intersectAndStore(key, otherKeys, destKey, aggregate, weights);
	}


}
