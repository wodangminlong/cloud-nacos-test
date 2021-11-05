package com.ml.service;

/**
 * OrderService
 *
 * @author dml
 * @date 2021/10/29 15:40
 */
public interface OrderService {

    /**
     * add order info
     *
     * @param orderId   order id
     * @param goodId    good id
     * @return  boolean
     */
    boolean addOrderInfo(String orderId, String goodId);

    /**
     * add secKill info
     *
     * @param goodId    good id
     * @return  int
     */
    int addSecKillInfo(String goodId);

    /**
     * close order
     *
     * @param orderId   order id
     * @return  int
     */
    int closeOrder(String orderId);

}
