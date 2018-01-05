package com.lc.base.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.lc.base.injection.component.AppComponent
import com.lc.base.injection.component.DaggerAppComponent
import com.lc.base.injection.module.AppModule

/**
 * Created by LiangCheng on 2017/12/20.
 */
open class BaseApplication : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        context = this

        /*初始化ARouter,模块路由框架*/
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var context: Context
    }
}