package com.lc.base.common

import android.app.Application
import com.lc.base.injection.component.AppComponent
import com.lc.base.injection.component.DaggerAppComponent
import com.lc.base.injection.module.AppModule

/**
 * Created by LiangCheng on 2017/12/20.
 */
class BaseApplication : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        initAppInjection()
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}