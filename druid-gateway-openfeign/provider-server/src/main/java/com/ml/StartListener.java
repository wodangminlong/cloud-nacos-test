package com.ml;

import com.ml.util.DruidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * start listener
 *
 * @author dml
 * @date 2021/11/4 10:40
 */
@Slf4j
@Component
public class StartListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private DruidUtils druidUtils;

    @Override
    public void onApplicationEvent(@Nonnull ApplicationReadyEvent event) {
        log.info("provider server start...");
        try {
            log.info("starting init master pool");
            druidUtils.initPool(true);
            log.info("init master pool success");
            log.info("starting init slave pool");
            druidUtils.initPool(false);
            log.info("init slave pool success");
        } catch (Exception e) {
            log.error("druid utils init pool has error: ", e);
        }
        log.info("provider server start success.");
    }
}
