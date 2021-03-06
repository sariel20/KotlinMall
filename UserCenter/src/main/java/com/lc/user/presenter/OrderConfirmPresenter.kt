package com.lc.user.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.user.data.protocol.Order
import com.lc.user.presenter.view.OrderConfirmView
import com.lc.user.service.OrderService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<OrderConfirmView>() {
    @Inject
    lateinit var orderService: OrderService

    fun getOrderById(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.getOrderById(orderId)
                .execute(object : BaseSubscriber<Order>(mView) {
                    override fun onNext(t: Order) {
                        mView.onGetOrderByIdResult(t)
                    }
                }, lifecycleProvider)

    }

    fun submitOrder(order: Order) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.submitOrder(order)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onSubmitOrderResult(t)
                    }
                }, lifecycleProvider)

    }

}