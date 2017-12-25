package com.lc.base.ui.fragment

import android.os.Bundle
import com.lc.base.common.BaseApplication
import com.lc.base.injection.component.ActivityComponent
import com.lc.base.injection.component.DaggerActivityComponent
import com.lc.base.injection.module.ActivityModule
import com.lc.base.injection.module.LifecycleProviderModule
import com.lc.base.presenter.BasePresenter
import com.lc.base.presenter.view.BaseView
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
open abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(text: String) {
        toast(text)
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