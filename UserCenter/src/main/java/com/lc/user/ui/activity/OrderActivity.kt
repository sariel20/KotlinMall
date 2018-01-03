package com.lc.user.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.lc.base.ui.activity.BaseActivity
import com.lc.user.R
import com.lc.user.common.OrderConstant
import com.lc.user.common.OrderStatus
import com.lc.user.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.activity_order.*

/**
 * Created by LiangCheng on 2018/1/3.
 */
class OrderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(supportFragmentManager, this)
        mOrderTab.setupWithViewPager(mOrderVp)

        /*根据点击切换Tab,设置默认值全部*/
        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS,
                OrderStatus.ORDER_ALL)
    }
}