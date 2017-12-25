package com.lc.base.presenter.view

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
}