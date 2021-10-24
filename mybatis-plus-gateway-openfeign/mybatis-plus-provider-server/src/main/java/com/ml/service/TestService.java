package com.ml.service;

import com.ml.model.TestModel;

import java.util.List;

/**
 * TestService
 *
 * @author Administrator
 * @date 2021/10/24 01:01
 */
public interface TestService {

    /**
     * get test list
     *
     * @return  list
     */
    List<TestModel> listGetTest();

    /**
     * add test
     *
     * @param name  name
     * @return  int
     */
    int addTest(String name);

    /**
     * update test
     *
     * @param id    id
     * @param name  name
     * @return  int
     */
    int updateTest(Long id, String name);

    /**
     * delete test
     *
     * @param id    id
     * @return  int
     */
    int deleteTest(Long id);

}
