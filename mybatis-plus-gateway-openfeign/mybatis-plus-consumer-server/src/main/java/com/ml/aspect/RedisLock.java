package com.ml.aspect;

import com.ml.Constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dml
 * @date 2021/12/8 10:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * lock key
     * @return  string
     */
    String lockKey() default Constant.DEFAULT_ORDER_LOCK_KEY_PREFIX;

    /**
     * expire time
     * @return time
     */
    long timeout() default Constant.DEFAULT_ORDER_LOCK_TIME;

}
