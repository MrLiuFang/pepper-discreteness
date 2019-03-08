package com.pepper.service.redis.jdk.serializer.impl;

import java.io.Closeable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.DependsOn;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.pepper.service.redis.jdk.serializer.RedisTemplateService;

/**
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = RedisTemplateService.class)
@DependsOn(value={"redisTemplate"})
public class RedisTemplateServiceImpl implements RedisTemplateService {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public <T> T execute(RedisCallback<T> action) {
		return redisTemplate.execute(action);
	}

	@Override
	public <T> T execute(SessionCallback<T> session) {
		return redisTemplate.execute(session);
	}

	@Override
	public List<Object> executePipelined(RedisCallback<?> action) {
		return redisTemplate.executePipelined(action);
	}

	@Override
	public List<Object> executePipelined(RedisCallback<?> action, RedisSerializer<?> resultSerializer) {
		return redisTemplate.executePipelined(action, resultSerializer);
	}

	@Override
	public List<Object> executePipelined(SessionCallback<?> session) {
		return redisTemplate.executePipelined(session);
	}

	@Override
	public List<Object> executePipelined(SessionCallback<?> session, RedisSerializer<?> resultSerializer) {
		return redisTemplate.executePipelined(session, resultSerializer);
	}

	@Override
	public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
		return redisTemplate.execute(script, keys, args);
	}

	@Override
	public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer,
			List<String> keys, Object... args) {
		return redisTemplate.execute(script, argsSerializer, resultSerializer, keys, args);
	}

	@Override
	public <T extends Closeable> T executeWithStickyConnection(RedisCallback<T> callback) {
		return redisTemplate.executeWithStickyConnection(callback);
	}

	@Override
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Long countExistingKeys(Collection<String> keys) {
		return redisTemplate.countExistingKeys(keys);
	}

	@Override
	public Boolean delete(String key) {
		return redisTemplate.delete(key);
	}

	@Override
	public Long delete(Collection<String> keys) {
		return redisTemplate.delete(keys);
	}

	@Override
	public Boolean unlink(String key) {
		return redisTemplate.unlink(key);
	}

	@Override
	public Long unlink(Collection<String> keys) {
		return redisTemplate.unlink(keys);
	}

	@Override
	public DataType type(String key) {
		return redisTemplate.type(key);
	}

	@Override
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public String randomKey() {
		return redisTemplate.randomKey();
	}

	@Override
	public void rename(String oldKey, String newKey) {
		redisTemplate.rename(oldKey, newKey);
	}

	@Override
	public Boolean renameIfAbsent(String oldKey, String newKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean expire(String key, long timeout, TimeUnit unit) {
		return redisTemplate.expire(key, timeout, unit);
	}

	@Override
	public Boolean expireAt(String key, Date date) {
		return redisTemplate.expireAt(key, date);
	}

	@Override
	public Boolean persist(String key) {
		return redisTemplate.persist(key);
	}

	@Override
	public Boolean move(String key, int dbIndex) {
		return redisTemplate.move(key, dbIndex);
	}

	@Override
	public byte[] dump(String key) {
		return redisTemplate.dump(key);
	}

	@Override
	public void restore(String key, byte[] value, long timeToLive, TimeUnit unit, boolean replace) {
		redisTemplate.restore(key, value, timeToLive, unit, replace);
	}

	@Override
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}

	@Override
	public Long getExpire(String key, TimeUnit timeUnit) {
		return redisTemplate.getExpire(key, timeUnit);
	}

	@Override
	public List<Object> sort(SortQuery<String> query) {
		return redisTemplate.sort(query);
	}

	@Override
	public <T> List<T> sort(SortQuery<String> query, RedisSerializer<T> resultSerializer) {
		return redisTemplate.sort(query, resultSerializer);
	}

	@Override
	public <T> List<T> sort(SortQuery<String> query, BulkMapper<T, Object> bulkMapper) {
		return redisTemplate.sort(query, bulkMapper);
	}

	@Override
	public <T, S> List<T> sort(SortQuery<String> query, BulkMapper<T, S> bulkMapper,
			RedisSerializer<S> resultSerializer) {
		return redisTemplate.sort(query, bulkMapper, resultSerializer);
	}

	@Override
	public Long sort(SortQuery<String> query, String storeKey) {
		return redisTemplate.sort(query, storeKey);
	}

	@Override
	public void watch(String key) {
		redisTemplate.watch(key);
	}

	@Override
	public void watch(Collection<String> keys) {
		redisTemplate.watch(keys);
	}

	@Override
	public void unwatch() {
		redisTemplate.unwatch();
	}

	@Override
	public void multi() {
		redisTemplate.multi();
	}

	@Override
	public void discard() {
		redisTemplate.discard();
	}

	@Override
	public List<Object> exec() {
		return redisTemplate.exec();
	}

	@Override
	public List<Object> exec(RedisSerializer<?> valueSerializer) {
		return redisTemplate.exec(valueSerializer);
	}

	@Override
	public List<RedisClientInfo> getClientList() {
		return redisTemplate.getClientList();
	}

	@Override
	public void killClient(String host, int port) {
		redisTemplate.killClient(host, port);
	}

	@Override
	public void slaveOf(String host, int port) {
		redisTemplate.slaveOf(host, port);
	}

	@Override
	public void slaveOfNoOne() {
		redisTemplate.slaveOfNoOne();
	}

	@Override
	public void convertAndSend(String destination, Object message) {
		redisTemplate.convertAndSend(destination, message);
	}

	@Override
	public ClusterOperations<String, Object> opsForCluster() {
		return redisTemplate.opsForCluster();
	}

	@Override
	public GeoOperations<String, Object> opsForGeo() {
		return redisTemplate.opsForGeo();
	}

	@Override
	public BoundGeoOperations<String, Object> boundGeoOps(String key) {
		return redisTemplate.boundGeoOps(key);
	}

	@Override
	public <HK, HV> HashOperations<String, HK, HV> opsForHash() {
		return redisTemplate.opsForHash();
	}

	@Override
	public <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(String key) {
		return redisTemplate.boundHashOps(key);
	}

	@Override
	public HyperLogLogOperations<String, Object> opsForHyperLogLog() {
		return redisTemplate.opsForHyperLogLog();
	}

	@Override
	public ListOperations<String, Object> opsForList() {
		return redisTemplate.opsForList();
	}

	@Override
	public BoundListOperations<String, Object> boundListOps(String key) {
		return redisTemplate.boundListOps(key);
	}

	@Override
	public SetOperations<String, Object> opsForSet() {
		return redisTemplate.opsForSet();
	}

	@Override
	public BoundSetOperations<String, Object> boundSetOps(String key) {
		return redisTemplate.boundSetOps(key);
	}

	@Override
	public ValueOperations<String, Object> opsForValue() {
		return redisTemplate.opsForValue();
	}

	@Override
	public BoundValueOperations<String, Object> boundValueOps(String key) {
		return redisTemplate.boundValueOps(key);
	}

	@Override
	public ZSetOperations<String, Object> opsForZSet() {
		return redisTemplate.opsForZSet();
	}

	@Override
	public BoundZSetOperations<String, Object> boundZSetOps(String key) {
		return redisTemplate.boundZSetOps(key);
	}

	@Override
	public RedisSerializer<?> getKeySerializer() {
		return redisTemplate.getKeySerializer();
	}

	@Override
	public RedisSerializer<?> getValueSerializer() {
		return redisTemplate.getValueSerializer();
	}

	@Override
	public RedisSerializer<?> getHashKeySerializer() {
		return redisTemplate.getHashKeySerializer();
	}

	@Override
	public RedisSerializer<?> getHashValueSerializer() {
		return redisTemplate.getHashValueSerializer();
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		redisTemplate.setBeanClassLoader(classLoader);
	}

}
