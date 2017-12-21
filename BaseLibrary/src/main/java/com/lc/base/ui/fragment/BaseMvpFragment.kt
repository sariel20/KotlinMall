package com.lc.base.ui.fragment

import android.os.Bundle
import com.lc.base.common.BaseApplication
import com.lc.base.injection.component.ActivityComponent
import com.lc.base.injection.component.DaggerActivityComponent
import com.lc.base.injection.module.ActivityModule
import com.lc.base.injection.module.LifecycleProviderModule
import com.lc.base.presenter.BasePresenter
import com.lc.base.presenter.view.BaseView
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
open abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {
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

        injectComponent()
    }

    //dagger2注册
    abstract fun injectComponent()


    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent(
                (activity.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(activity))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}