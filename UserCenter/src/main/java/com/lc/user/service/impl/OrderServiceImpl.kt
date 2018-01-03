package com.lc.user.service.impl

import com.lc.base.ext.convert
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

}