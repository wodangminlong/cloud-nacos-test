package com.ml.mapper.master;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ml.model.TestModel;

/**
 * TestMapper
 *
 * @author Administrator
 * @date 2021/10/24 01:00
 */
@DS("master")
public interface TestMapper extends BaseMapper<TestModel> {
}
