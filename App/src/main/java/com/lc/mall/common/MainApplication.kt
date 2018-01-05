package com.lc.mall.common

import cn.jpush.android.api.JPushInterface
import com.lc.base.common.BaseApplication

/**
 * Created by LiangCheng on 2018/1/5.
 */
class MainApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        /*初始化极光推送*/
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }


}