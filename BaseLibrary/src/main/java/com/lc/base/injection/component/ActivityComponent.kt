package com.lc.base.injection.component

import android.app.Activity
import android.content.Context
import com.lc.base.injection.ActivityScope
import com.lc.base.injection.module.ActivityModule
import com.lc.base.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Component

/**
 * Created by LiangCheng on 2017/12/20.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class)
        , modules = arrayOf(ActivityModule::class, LifecycleProviderModule::class))
interface ActivityComponent {
    fun activity(): Activity
    fun context(): Context
    fun lifecycleProvider(): LifecycleProvider<*>
}