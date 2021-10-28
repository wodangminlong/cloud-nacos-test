package com.ml.mapper.slave;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ml.model.TestModel;

/**
 * @author Administrator
 * @date 2021/10/28 12:17
 */
@DS("slave")
public interface SlaveTestMapper extends BaseMapper<TestModel> {
}
