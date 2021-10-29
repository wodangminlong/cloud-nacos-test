package com.ml.mapper.master;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ml.model.OrderModel;

/**
 * OrderMapper
 *
 * @author dml
 * @date 2021/10/29 15:42
 */
@DS("master")
public interface OrderMapper extends BaseMapper<OrderModel> {
}
