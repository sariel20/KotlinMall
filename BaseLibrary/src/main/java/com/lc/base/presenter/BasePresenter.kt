package com.lc.base.presenter

import com.lc.base.presenter.view.BaseView

/**
 * Created by LiangCheng on 2017/12/19.
 */
open class BasePresenter<T : BaseView> {
    lateinit var mView: T
}