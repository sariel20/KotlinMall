package com.lc.pay.service.impl

import com.lc.base.ext.convert
import com.lc.base.ext.convertBoolean
import com.lc.pay.data.repository.PayRepository
import com.lc.pay.service.PayService
import rx.Observable
import javax.inject.Inject

/*
    支付 实现类
 */
class PayServiceImpl @Inject constructor() : PayService {
    @Inject
    lateinit var repository: PayRepository

    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {
        return repository.getPaySign(orderId, totalPrice).convert()
    }

    override fun payOrder(orderId: Int): Observable<Boolean> {
        return repository.payOrder(orderId).convertBoolean()
    }
}