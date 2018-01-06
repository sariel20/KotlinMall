package com.lc.message.provider

import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.lc.provider.PushProvider
import com.lc.provider.router.RouterPath

/**
 * Created by LiangCheng on 2018/1/6.
 */
@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
class PushProviderImpl : PushProvider {
    private var mContext: Context? = null
    override fun getPushId(): String {
        return JPushInterface.getRegistrationID(mContext)
    }

    override fun init(context: Context?) {
        mContext = context
    }
}