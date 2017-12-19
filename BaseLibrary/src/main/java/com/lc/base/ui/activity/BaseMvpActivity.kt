package com.lc.base.ui.activity

import com.lc.base.presenter.BasePresenter
import com.lc.base.presenter.view.BaseView

/**
 * Created by LiangCheng on 2017/12/19.
 */
open class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {
    override fun hideLoading() {
    }

    override fun onError() {
    }

    override fun showLoading() {
    }

    lateinit var mPresenter: T
}