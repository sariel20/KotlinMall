package com.lc.provider

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Created by LiangCheng on 2018/1/6.
 */
interface PushProvider : IProvider {
    fun getPushId(): String
}