package com.ml.serviceimpl;

import com.ml.mapper.master.OrderMapper;
import com.ml.mapper.master.SecKillMapper;
import com.ml.model.OrderModel;
import com.ml.model.SecKillModel;
import com.ml.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * order service impl
 *
 * @author dml
 * @date 2021/10/29 15:41
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private SecKillMapper secKillMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addOrderInfo(String orderId, String goodId) {
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderId(orderId);
        orderModel.setGoodId(goodId);
        Date date = new Date();
        orderModel.setCreateTime(date);
        orderMapper.insert(orderModel);
        SecKillModel secKillModel = new SecKillModel();
        secKillModel.setGoodId(goodId);
        secKillModel.setCreateTime(date);
        secKillModel.setStatus(1);
        secKillMapper.insert(secKillModel);
        return true;
    }

    @Override
    public int addSecKillInfo(String goodId) {
        SecKillModel secKillModel = new SecKillModel();
        secKillModel.setGoodId(goodId);
        secKillModel.setCreateTime(new Date());
        secKillModel.setStatus(0);
        return secKillMapper.insert(secKillModel);
    }
}
