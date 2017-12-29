package com.lc.provider.common

import com.alibaba.android.arouter.launcher.ARouter
import com.lc.base.common.BaseConstant
import com.lc.base.utils.AppPrefsUtils
import com.lc.provider.router.RouterPath

/**
 * Created by 18480 on 2017/12/27.
 */
fun isLogined(): Boolean {
    return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN).isNotEmpty().not()
}

fun afterLogin(method: () -> Unit) {
    if (isLogined()) {
        /*跨模块跳转到登录界面*/
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
    } else {
        method()
    }
}