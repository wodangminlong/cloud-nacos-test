package com.ml.serviceimpl;

import com.ml.mapper.master.TestMapper;
import com.ml.mapper.slave.SlaveTestMapper;
import com.ml.model.TestModel;
import com.ml.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TestServiceImpl
 *
 * @author Administrator
 * @date 2021/10/24 01:02
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;
    @Resource
    private SlaveTestMapper slaveTestMapper;

    @Override
    public List<TestModel> listGetTest() {
        return slaveTestMapper.selectList(null);
    }

    @Override
    public int addTest(String name) {
        TestModel testModel = new TestModel();
        testModel.setName(name);
        return testMapper.insert(testModel);
    }

    @Override
    public int updateTest(Long id, String name) {
        TestModel testModel = new TestModel();
        testModel.setId(id);
        testModel.setName(name);
        return testMapper.updateById(testModel);
    }

    @Override
    public int deleteTest(Long id) {
        return testMapper.deleteById(id);
    }
}
