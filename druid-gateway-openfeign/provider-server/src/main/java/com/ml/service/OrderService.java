package com.ml.service;

import com.ml.IdWorkerUtils;
import com.ml.util.MysqlBaseUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * order service
 *
 * @author dml
 * @date 2021/10/29 14:23
 */
@Component
public class OrderService {

    @Resource
    private MysqlBaseUtils mysqlBaseUtils;

    /**
     * add order info
     *
     * @param orderId   order id
     * @param goodId    good id
     * @return  boolean
     */
    public boolean addOrderInfo(String orderId, String goodId) {
        String addOrderSql = "INSERT INTO tb_order (id,order_id,good_id,create_time,status) VALUES (?,?,?,NOW(3),0);";
        String addSecKillSql = "INSERT INTO tb_seckill (id,good_id,create_time,status) VALUES (?,?,NOW(3),1);";
        List<List<Object>> paramsGroupList = new ArrayList<>();
        paramsGroupList.add(Arrays.asList(IdWorkerUtils.nextId(), orderId, goodId));
        paramsGroupList.add(Arrays.asList(IdWorkerUtils.nextId(), goodId));
        return mysqlBaseUtils.batchUpdateSql(Arrays.asList(addOrderSql, addSecKillSql), paramsGroupList);

    }

    /**
     * add seckill info
     *
     * @param goodId    good id
     * @return  int
     * @throws SQLException SQLException
     */
    public int addSecKillInfo(String goodId) throws SQLException {
        String addSecKillSql = "INSERT INTO tb_seckill (id,good_id,create_time,status) VALUES (?,?,NOW(3),0);";
        return mysqlBaseUtils.updateSql(addSecKillSql, Arrays.asList(IdWorkerUtils.nextId(), goodId));

    }

    /**
     * close order
     *
     * @param orderId   order id
     * @return  int
     * @throws SQLException SQLException
     */
    public int closeOrder(String orderId) throws SQLException {
        String updateSql = "UPDATE tb_order SET status = 2 WHERE order_id = ? AND status = 0;";
        return mysqlBaseUtils.updateSql(updateSql, Collections.singletonList(orderId));
    }

}
