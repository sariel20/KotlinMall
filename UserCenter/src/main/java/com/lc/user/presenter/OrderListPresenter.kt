package com.lc.user.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.user.data.protocol.Order
import com.lc.user.presenter.view.OrderListView
import com.lc.user.service.OrderService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class OrderListPresenter @Inject constructor() : BasePresenter<OrderListView>() {
    @Inject
    lateinit var orderService: OrderService

    fun getOrderList(orderStatus: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.getOrderList(orderStatus)
                .execute(object : BaseSubscriber<MutableList<Order>?>(mView) {
                    override fun onNext(t: MutableList<Order>?) {
                        mView.onGetOrderListResult(t)
                    }
                }, lifecycleProvider)

    }

    fun confirmOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.confirmOrder(orderId)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onConfirmOrderResult(t)
                    }
                }, lifecycleProvider)

    }

    fun cancelOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.cancelOrder(orderId)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onCancelOrderResult(t)
                    }
                }, lifecycleProvider)

    }

}