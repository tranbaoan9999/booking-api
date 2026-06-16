package com.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/keys")
    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }

    @GetMapping("/value")
    public Object getValue(
            @RequestParam String key
    ) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/ttl")
    public Map<String, Object> getTtl(
            @RequestParam String key
    ) {
        Long ttl = redisTemplate.getExpire(
                key,
                TimeUnit.SECONDS
        );

        return Map.of(
                "key", key,
                "ttl", ttl
        );
    }

    @DeleteMapping("/delete")
    public String deleteKey(
            @RequestParam String key
    ) {
        Boolean deleted = redisTemplate.delete(key);

        return Boolean.TRUE.equals(deleted)
                ? "Deleted"
                : "Key not found";
    }

    @DeleteMapping("/clear")
    public String clearAll() {
        Set<String> keys = redisTemplate.keys("*");

        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }

        return "All cache cleared";
    }
}