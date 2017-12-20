package com.lc.base.ui.activity

import android.os.Bundle
import com.lc.base.common.BaseApplication
import com.lc.base.injection.component.ActivityComponent
import com.lc.base.injection.component.DaggerActivityComponent
import com.lc.base.injection.module.ActivityModule
import com.lc.base.presenter.BasePresenter
import com.lc.base.presenter.view.BaseView
import javax.inject.Inject

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

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
    }

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent(
                (application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .build()
    }
}