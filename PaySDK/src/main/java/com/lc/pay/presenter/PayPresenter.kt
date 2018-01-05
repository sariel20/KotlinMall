package com.lc.pay.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.pay.presenter.view.PayView
import com.lc.pay.service.PayService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class PayPresenter @Inject constructor() : BasePresenter<PayView>() {
    @Inject
    lateinit var payService: PayService

    fun getPaySign(orderId: Int, totalPrice: Long) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        payService.getPaySign(orderId, totalPrice)
                .execute(object : BaseSubscriber<String>(mView) {
                    override fun onNext(t: String) {
                        mView.onGetPaySignResult(t)
                    }
                }, lifecycleProvider)

    }

    fun payOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        payService.payOrder(orderId)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onPayOrderResult(t)
                    }
                }, lifecycleProvider)

    }


}