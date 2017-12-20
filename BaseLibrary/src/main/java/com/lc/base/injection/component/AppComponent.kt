package com.lc.base.injection.component

import android.content.Context
import com.lc.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun context(): Context
}