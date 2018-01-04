package com.lc.user.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.user.data.protocol.Order

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface OrderListView : BaseView {

    fun onGetOrderListResult(result: MutableList<Order>?)
    fun onConfirmOrderResult(result: Boolean)
    fun onCancelOrderResult(result: Boolean)
}