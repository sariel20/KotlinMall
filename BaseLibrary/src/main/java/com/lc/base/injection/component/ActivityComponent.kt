package com.lc.base.injection.component

import android.app.Activity
import com.lc.base.injection.ActivityScope
import com.lc.base.injection.module.ActivityModule
import dagger.Component

/**
 * Created by LiangCheng on 2017/12/20.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun activity(): Activity
}