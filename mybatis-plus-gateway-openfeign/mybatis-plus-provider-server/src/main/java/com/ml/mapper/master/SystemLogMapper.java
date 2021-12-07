package com.ml.mapper.master;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ml.model.SystemLogModel;

/**
 * @author dml
 * @date 2021/12/7 11:08
 */
@DS("master")
public interface SystemLogMapper extends BaseMapper<SystemLogModel> {
}
