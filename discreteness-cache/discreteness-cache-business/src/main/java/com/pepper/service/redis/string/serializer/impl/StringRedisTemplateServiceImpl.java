package com.pepper.service.redis.string.serializer.impl;

import java.io.Closeable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.BulkMapper;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.service.redis.string.serializer.StringRedisTemplateService;

/**
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = StringRedisTemplateService.class)
public class StringRedisTemplateServiceImpl implements StringRedisTemplateService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public <T> T execute(RedisCallback<T> action) {
		return stringRedisTemplate.execute(action);
	}

	@Override
	public <T> T execute(SessionCallback<T> session) {
		return stringRedisTemplate.execute(session);
	}

	@Override
	public List<Object> executePipelined(RedisCallback<?> action) {
		return stringRedisTemplate.executePipelined(action);
	}

	@Override
	public List<Object> executePipelined(RedisCallback<?> action, RedisSerializer<?> resultSerializer) {
		return stringRedisTemplate.executePipelined(action, resultSerializer);
	}

	@Override
	public List<Object> executePipelined(SessionCallback<?> session) {
		return stringRedisTemplate.executePipelined(session);
	}

	@Override
	public List<Object> executePipelined(SessionCallback<?> session, RedisSerializer<?> resultSerializer) {
		return stringRedisTemplate.executePipelined(session, resultSerializer);
	}

	@Override
	public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
		return stringRedisTemplate.execute(script, keys, args);
	}

	@Override
	public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer,
			List<String> keys, Object... args) {
		return stringRedisTemplate.execute(script, argsSerializer, resultSerializer, keys, args);
	}

	@Override
	public <T extends Closeable> T executeWithStickyConnection(RedisCallback<T> callback) {
		return stringRedisTemplate.executeWithStickyConnection(callback);
	}

	@Override
	public Boolean hasKey(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	@Override
	public Boolean delete(String key) {
		return stringRedisTemplate.delete(key);
	}

	@Override
	public Long delete(Collection<String> keys) {
		return stringRedisTemplate.delete(keys);
	}

	@Override
	public DataType type(String key) {
		return stringRedisTemplate.type(key);
	}

	@Override
	public Set<String> keys(String pattern) {
		return stringRedisTemplate.keys(pattern);
	}

	@Override
	public String randomKey() {
		return stringRedisTemplate.randomKey();
	}

	@Override
	public void rename(String oldKey, String newKey) {
		stringRedisTemplate.rename(oldKey, newKey);
	}

	@Override
	public Boolean renameIfAbsent(String oldKey, String newKey) {
		return stringRedisTemplate.renameIfAbsent(oldKey, newKey);
	}

	@Override
	public Boolean expire(String key, long timeout, TimeUnit unit) {
		return stringRedisTemplate.expire(key, timeout, unit);
	}

	@Override
	public Boolean expireAt(String key, Date date) {
		return stringRedisTemplate.expireAt(key, date);
	}

	@Override
	public Boolean persist(String key) {
		return stringRedisTemplate.persist(key);
	}

	@Override
	public Boolean move(String key, int dbIndex) {
		return stringRedisTemplate.move(key, dbIndex);
	}

	@Override
	public byte[] dump(String key) {
		return stringRedisTemplate.dump(key);
	}

	@Override
	public void restore(String key, byte[] value, long timeToLive, TimeUnit unit) {
		stringRedisTemplate.restore(key, value, timeToLive, unit);
	}

	@Override
	public Long getExpire(String key) {
		return stringRedisTemplate.getExpire(key);
	}

	@Override
	public Long getExpire(String key, TimeUnit timeUnit) {
		return stringRedisTemplate.getExpire(key);
	}

	@Override
	public List<String> sort(SortQuery<String> query) {
		return stringRedisTemplate.sort(query);
	}

	@Override
	public <T> List<T> sort(SortQuery<String> query, RedisSerializer<T> resultSerializer) {
		return stringRedisTemplate.sort(query, resultSerializer);
	}

	@Override
	public <T> List<T> sort(SortQuery<String> query, BulkMapper<T, String> bulkMapper) {
		return stringRedisTemplate.sort(query, bulkMapper);
	}

	@Override
	public <T, S> List<T> sort(SortQuery<String> query, BulkMapper<T, S> bulkMapper,
			RedisSerializer<S> resultSerializer) {
		return stringRedisTemplate.sort(query, bulkMapper, resultSerializer);
	}

	@Override
	public Long sort(SortQuery<String> query, String storeKey) {
		return stringRedisTemplate.sort(query, storeKey);
	}

	@Override
	public void watch(String key) {
		stringRedisTemplate.watch(key);
	}

	@Override
	public void watch(Collection<String> keys) {
		stringRedisTemplate.watch(keys);		
	}

	@Override
	public void unwatch() {
		stringRedisTemplate.unwatch();
	}

	@Override
	public void multi() {
		stringRedisTemplate.multi();
	}

	@Override
	public void discard() {
		stringRedisTemplate.discard();
	}

	@Override
	public List<Object> exec() {
		return stringRedisTemplate.exec();
	}

	@Override
	public List<Object> exec(RedisSerializer<?> valueSerializer) {
		return stringRedisTemplate.exec(valueSerializer);
	}

	@Override
	public List<RedisClientInfo> getClientList() {
		return stringRedisTemplate.getClientList();
	}

	@Override
	public void killClient(String host, int port) {
		stringRedisTemplate.killClient(host, port);
	}

	@Override
	public void slaveOf(String host, int port) {
		stringRedisTemplate.slaveOf(host, port);
	}

	@Override
	public void slaveOfNoOne() {
		stringRedisTemplate.slaveOfNoOne();
	}

	@Override
	public void convertAndSend(String destination, Object message) {
		stringRedisTemplate.convertAndSend(destination, message);
	}

	@Override
	public ClusterOperations<String, String> opsForCluster() {
		return stringRedisTemplate.opsForCluster();
	}

	@Override
	public GeoOperations<String, String> opsForGeo() {
		return stringRedisTemplate.opsForGeo();
	}

	@Override
	public BoundGeoOperations<String, String> boundGeoOps(String key) {
		return stringRedisTemplate.boundGeoOps(key);
	}

	@Override
	public <HK, HV> HashOperations<String, HK, HV> opsForHash() {
		return stringRedisTemplate.opsForHash();
	}

	@Override
	public <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(String key) {
		return stringRedisTemplate.boundHashOps(key);
	}

	@Override
	public HyperLogLogOperations<String, String> opsForHyperLogLog() {
		return stringRedisTemplate.opsForHyperLogLog();
	}

	@Override
	public ListOperations<String, String> opsForList() {
		return stringRedisTemplate.opsForList();
	}

	@Override
	public BoundListOperations<String, String> boundListOps(String key) {
		return stringRedisTemplate.boundListOps(key);
	}

	@Override
	public SetOperations<String, String> opsForSet() {
		return stringRedisTemplate.opsForSet();
	}

	@Override
	public BoundSetOperations<String, String> boundSetOps(String key) {
		return stringRedisTemplate.boundSetOps(key);
	}

	@Override
	public ValueOperations<String, String> opsForValue() {
		return stringRedisTemplate.opsForValue();
	}

	@Override
	public BoundValueOperations<String, String> boundValueOps(String key) {
		return stringRedisTemplate.boundValueOps(key);
	}

	@Override
	public ZSetOperations<String, String> opsForZSet() {
		return stringRedisTemplate.opsForZSet();
	}

	@Override
	public BoundZSetOperations<String, String> boundZSetOps(String key) {
		return stringRedisTemplate.boundZSetOps(key);
	}

	@Override
	public RedisSerializer<?> getKeySerializer() {
		return stringRedisTemplate.getKeySerializer();
	}

	@Override
	public RedisSerializer<?> getValueSerializer() {
		return stringRedisTemplate.getValueSerializer();
	}

	@Override
	public RedisSerializer<?> getHashKeySerializer() {
		return stringRedisTemplate.getHashKeySerializer();
	}

	@Override
	public RedisSerializer<?> getHashValueSerializer() {
		return stringRedisTemplate.getHashValueSerializer();
	}

	@Override
	public Long countExistingKeys(Collection<String> keys) {
		return stringRedisTemplate.countExistingKeys(keys);
	}

	@Override
	public Boolean unlink(String key) {
		return stringRedisTemplate.unlink(key);
	}

	@Override
	public Long unlink(Collection<String> keys) {
		return stringRedisTemplate.unlink(keys);
	}

	@Override
	public void restore(String key, byte[] value, long timeToLive, TimeUnit unit, boolean replace) {
		stringRedisTemplate.restore(key, value, timeToLive, unit, replace);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		stringRedisTemplate.setBeanClassLoader(classLoader);
	}


}
