package com.lc.user.service.impl

import com.lc.base.ext.convert
import com.lc.base.ext.convertBoolean
import com.lc.user.data.protocol.Order
import com.lc.user.data.repository.OrderRepository
import com.lc.user.service.OrderService
import rx.Observable
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class OrderServiceImpl @Inject constructor() : OrderService {

    @Inject
    lateinit var repository: OrderRepository

    override fun getOrderById(orderId: Int): Observable<Order> {
        return repository.getOrderById(orderId).convert()
    }

    override fun submitOrder(order: Order): Observable<Boolean> {
        return repository.submitOrder(order).convertBoolean()
    }

    override fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?> {
        return repository.getOrderList(orderStatus).convert()
    }

    override fun cancelOrder(orderId: Int): Observable<Boolean> {
        return repository.cancelOrder(orderId).convertBoolean()
    }

    override fun confirmOrder(orderId: Int): Observable<Boolean> {
        return repository.confirmOrder(orderId).convertBoolean()
    }
}