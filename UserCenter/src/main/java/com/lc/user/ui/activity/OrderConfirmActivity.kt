package com.lc.user.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lc.base.ui.activity.BaseActivity
import com.lc.provider.router.RouterPath
import com.lc.user.R

/**
 * Created by ${LiangCheng} on 2018/1/2.
 */
@Route(path = RouterPath.UserCenter.PATH_ORDER)
class OrderConfirmActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
    }
}