package com.lc.base.injection.module

import android.app.Activity
import com.lc.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Module
class ActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return this.activity
    }
}
