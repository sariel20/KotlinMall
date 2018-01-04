package com.lc.user.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.user.data.protocol.Order

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface OrderDetailView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}