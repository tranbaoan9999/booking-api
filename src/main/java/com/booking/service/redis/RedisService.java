package com.booking.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {

        Boolean result = redisTemplate.delete(key);

        return Boolean.TRUE.equals(result);
    }

    public boolean exists(String key) {

        Boolean result = redisTemplate.hasKey(key);

        return Boolean.TRUE.equals(result);
    }

    public boolean expire(String key, Duration timeout) {

        Boolean result = redisTemplate.expire(key, timeout);

        return Boolean.TRUE.equals(result);
    }

    public boolean lock(
            String key,
            Object value,
            Duration timeout
    ) {

        Boolean result = redisTemplate
                .opsForValue()
                .setIfAbsent(key, value, timeout);

        return Boolean.TRUE.equals(result);
    }

    public boolean unlock(String key) {

        Boolean result = redisTemplate.delete(key);

        return Boolean.TRUE.equals(result);
    }

    public void hashSet(
            String key,
            String field,
            Object value
    ) {

        redisTemplate
                .opsForHash()
                .put(key, field, value);
    }

    public Object hashGet(
            String key,
            String field
    ) {

        return redisTemplate
                .opsForHash()
                .get(key, field);
    }

    public void pushToList(
            String key,
            Object value
    ) {

        redisTemplate
                .opsForList()
                .rightPush(key, value);
    }

    public Object popFromList(String key) {

        return redisTemplate
                .opsForList()
                .leftPop(key);
    }

    public void addToSet(
            String key,
            Object value
    ) {

        redisTemplate
                .opsForSet()
                .add(key, value);
    }

    public Set<Object> getSetMembers(String key) {

        return redisTemplate
                .opsForSet()
                .members(key);
    }
}
