package com.ml.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * test service
 *
 * @author dml
 * @date 2021/11/30 11:30
 */
@Slf4j
@Service
public class TestService {

    /**
     * test async
     *
     * @throws InterruptedException exception
     */
    @Async
    public void testAsync() throws InterruptedException {
        Thread.sleep(2000);
        log.info("test async success");
    }

}
