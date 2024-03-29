package com.tilitili.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisCache implements InitializingBean {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	
	public void setValue(String key,Object value,int expireTime){
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	public void setValue(String key, Object value){
	    redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置key为永久有效
     */
    public void persistKey(String key){
	    redisTemplate.persist(key);
    }
	
	public Object getValue(String key){
		return redisTemplate.opsForValue().get(key);
	}
	
	public void addSet(String key,List<?> values,int expireTime){
		redisTemplate.opsForSet().add(key, values.toArray());
		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}

	public void addSet(String key, List<?> values){
		redisTemplate.opsForSet().add(key, values.toArray());
	}

	public void addMap(String key, Map<?, ?> map) {
    	redisTemplate.opsForHash().putAll(key, map);
	}

	public void addMapValue(String key, String hashKey, Object value) {
    	redisTemplate.opsForHash().put(key, hashKey, value);
	}

	public Object getMapValue(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	public Object removeMapValue(String key, String hashKey) {
		return redisTemplate.opsForHash().delete(key, hashKey);
	}

	public Long increment(String key, Long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public Long increment(String key) {
		return redisTemplate.opsForValue().increment(key);
	}
	
	public Set<Object> memberSet(String key){
		return redisTemplate.opsForSet().members(key);
	}

	public void removeSetValue(String key, String value){
        redisTemplate.opsForSet().remove(key, value);
    }
	
	public void delete(String key){
		redisTemplate.delete(key);
	}

	public void deleteBatch(List<String> list){
		redisTemplate.delete(list);
	}
	
	/**
	 * 通配符批量删除
	 */
	public void deleteBatchByPattern(String... pattern){
		for (String p : pattern) {  
			redisTemplate.delete(redisTemplate.keys(p));  
        } 
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.error("rediscache init finished");
	}

    /**
     * 是否存在key
     */
	public boolean exists(String key){
	    return redisTemplate.hasKey(key);
    }

	public boolean existsHashKey(String key, String hashKey){
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}
//    /**
//     * 增长
//     */
//    public void addValue(String key, long value){
//      if(exists(key)){
//        long num = Long.parseLong(String.valueOf(redisTemplate.opsForValue().get(key)));
//        num += value;
//        redisTemplate.opsForValue().set(key, num);
//      } else {
//        redisTemplate.opsForValue().set(key, value);
//      }
//    }

    /**
     * 若是key不存在则设置成功
     */
    public Boolean setNotExist(String key, String value, int expireTime, TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
    }
}
