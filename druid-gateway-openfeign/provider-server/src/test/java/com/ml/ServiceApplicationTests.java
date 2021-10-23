package com.ml;

import com.ml.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceApplicationTests {

    @Test
    void contextLoads() {
        TestController testController = new TestController();
        System.out.println(testController.test());
    }

}
