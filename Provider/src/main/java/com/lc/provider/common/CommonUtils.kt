package com.lc.provider.common

import com.lc.base.common.BaseConstant
import com.lc.base.utils.AppPrefsUtils

/**
 * Created by 18480 on 2017/12/27.
 */
fun isLogined(): Boolean {
    return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN).isNotEmpty().not()
}