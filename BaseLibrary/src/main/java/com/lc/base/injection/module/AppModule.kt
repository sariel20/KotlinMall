package com.lc.base.injection.module

import android.content.Context
import com.lc.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Module
class AppModule(private val context: BaseApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return this.context
    }
}
