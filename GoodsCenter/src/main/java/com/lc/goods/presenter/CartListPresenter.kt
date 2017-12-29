package com.lc.goods.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.goods.data.protocol.CartGoods
import com.lc.goods.presenter.view.CartListView
import com.lc.goods.service.CartService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class CartListPresenter @Inject constructor() : BasePresenter<CartListView>() {
    @Inject
    lateinit var cartService: CartService

    fun getCartList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        cartService.getCartList()
                .execute(object : BaseSubscriber<MutableList<CartGoods>?>(mView) {
                    override fun onNext(t: MutableList<CartGoods>?) {
                        mView.onGetCartListResult(t)
                    }
                }, lifecycleProvider)

    }


}