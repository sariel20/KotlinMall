package com.lc.pay.presenter.view

import com.lc.base.presenter.view.BaseView

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface PayView : BaseView {

    fun onGetPaySignResult(result: String)
    fun onPayOrderResult(result: Boolean)
}