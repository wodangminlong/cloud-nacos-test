package com.ml.aspect;

import com.ml.ApiErrorCode;
import com.ml.ApiResponse;
import com.ml.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dml
 * @date 2021/12/8 10:31
 */
@Slf4j
@Aspect
@Component
public class RedisLockAspect {

    @Resource
    private RedisUtils redisUtils;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    @Pointcut("@annotation(redisLock)")
    public void pointCut(RedisLock redisLock) {
        // do something
    }

    @Around(value = "pointCut(redisLock)", argNames = "proceedingJoinPoint,redisLock")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RedisLock redisLock) throws Throwable {
        currentTime.set(System.currentTimeMillis());
        String lockKey = redisLock.lockKey();
        long timeout = redisLock.timeout();
        try {
            for (; ; ) {
                // try to get lock
                boolean getLock = redisUtils.lock(lockKey, lockKey, timeout);
                if (getLock) {
                    MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                    log.info("{}.{} get lock", proceedingJoinPoint.getTarget().getClass().getName(), signature.getName());
                    currentTime.remove();
                    return proceedingJoinPoint.proceed();
                }
                long end = System.currentTimeMillis() - currentTime.get();
                if (end >= timeout) {
                    currentTime.remove();
                    return ApiResponse.error(ApiErrorCode.OPERATION_IS_TOO_FREQUENT);
                }
            }
        } finally {
            redisUtils.releaseLock(lockKey);
        }
    }

}
