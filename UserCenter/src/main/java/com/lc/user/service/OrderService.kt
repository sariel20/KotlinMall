package com.lc.user.service

import com.lc.user.data.protocol.Order
import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface OrderService {
    /*
        根据ID查询订单
     */
    fun getOrderById(orderId: Int): Observable<Order>

    /*
       提交订单
    */
    fun submitOrder(order: Order): Observable<Boolean>

    /*
       根据状态查询订单列表
    */
    fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?>
}