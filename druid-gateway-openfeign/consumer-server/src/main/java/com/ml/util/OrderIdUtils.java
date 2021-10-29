package com.ml.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * order id utils
 *
 * @author dml
 * @date 2021/10/29 17:10
 */
@Component
public class OrderIdUtils {

    @Resource
    private RedisUtils redisUtils;

    public synchronized String getOrderId() {
        String key = "ORDER_ID";
        Long incrValue = redisUtils.incr(key, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long timeout = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        redisUtils.expire(key, String.valueOf(incrValue), timeout);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date()) + String.format("%08d", incrValue);
    }
}
