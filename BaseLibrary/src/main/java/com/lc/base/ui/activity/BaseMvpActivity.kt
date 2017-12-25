package com.lc.base.ui.activity

import android.os.Bundle
import com.lc.base.common.BaseApplication
import com.lc.base.injection.component.ActivityComponent
import com.lc.base.injection.component.DaggerActivityComponent
import com.lc.base.injection.module.ActivityModule
import com.lc.base.injection.module.LifecycleProviderModule
import com.lc.base.presenter.BasePresenter
import com.lc.base.presenter.view.BaseView
import com.lc.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
open abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()

        injectComponent()

        mLoadingDialog = ProgressLoading.create(this)
    }

    //dagger2注册
    abstract fun injectComponent()


    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent(
                (application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}