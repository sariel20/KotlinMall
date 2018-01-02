package com.lc.goods.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.goods.data.protocol.CartGoods

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface CartListView : BaseView {

    fun onGetCartListResult(result: MutableList<CartGoods>?)
    fun onDeleteCartListResult(result: Boolean?)
    fun onSubmipCartResult(result: Int)
}