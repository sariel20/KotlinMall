package com.lc.goods.ui.activity

import android.os.Bundle
import com.lc.base.ui.activity.BaseActivity
import com.lc.goods.R
import com.lc.goods.ui.fragment.CartFragment

/**
 * Created by LiangCheng on 2017/12/30.
 */
class CartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        /*获取head显示回退按钮*/
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentCart)
        (fragment as CartFragment).setBackVisible(true)
    }
}