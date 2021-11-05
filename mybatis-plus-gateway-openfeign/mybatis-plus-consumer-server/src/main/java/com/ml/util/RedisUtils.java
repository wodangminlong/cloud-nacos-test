package com.ml.util;

import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * redis utils
 *
 * @author dml
 * @date 2021/10/29 15:35
 */
@Slf4j
@Component
public class RedisUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * get value by key
     */
    public String get(String key) {
        return StringUtils.isBlank(key) ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * save key,value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * save key,value,expire seconds
     *
     * @param key       key
     * @param value     value
     * @param timeout   timeout seconds
     */
    private void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * save key,value,expire seconds
     *
     * @param key key
     * @param value value
     * @param seconds seconds
     */
    public void expire(String key, String value, long seconds) {
        try {
            if (seconds > 0) {
                set(key, value, seconds);
            } else {
                set(key, value);
            }
        } catch (Exception e) {
            log.error("RedisUtils expire has error:", e);
        }
    }

    /**
     * increment
     *
     * @param key           key
     * @param incrValue     incrValue
     * @return              Long
     */
    public Long incr(String key, long incrValue){
        return stringRedisTemplate.opsForValue().increment(key, incrValue);
    }

    /**
     * decrease
     *
     * @param key           key
     * @param decrValue     decrValue
     * @return              Long
     */
    public Long decr(String key, long decrValue){
        return stringRedisTemplate.opsForValue().decrement(key, decrValue);
    }

    /**
     * delete key
     *
     * @param keys keys
     */
    public void delete(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                stringRedisTemplate.delete(keys[0]);
            } else {
                stringRedisTemplate.delete(Arrays.asList(keys));
            }
        }
    }

    /**
     * lock
     * @param key           lock key
     * @param value         lock value
     * @param releaseTime   release  microsecond
     *
     * ps: When locking, we need to make sure that the execution time of code block is less than the release time of lock,
     *     otherwise it will cause the lock to re-enter the bug and cause a major accident
     *
     *     However, the non-high concurrency interface does not need to call the release lock, and the lock will be
     *     released automatically after the release time, so the lock and release lock do not necessarily appear in pairs
     *
     * @return      boolean
     */
    public boolean lock(String key, String value,long releaseTime) {
        // try to get lock key
        Boolean boo = stringRedisTemplate.opsForValue().setIfAbsent(key, value, releaseTime, TimeUnit.MICROSECONDS);
        return boo != null && boo;
    }

    /**
     * release lock
     * @param key   lock key
     */
    public void releaseLock(String key) {
        delete(key);
    }

}
