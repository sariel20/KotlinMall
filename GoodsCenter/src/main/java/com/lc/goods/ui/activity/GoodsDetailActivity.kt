package com.lc.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseActivity
import com.lc.goods.R
import com.lc.goods.ui.adapter.GoodsDetailVpAdapter
import com.lc.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_goods_detail.*

/**
 * Created by LiangCheng on 2017/12/28.
 */
class GoodsDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
    }

    /*关联viewpage 与 fragment*/
    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED

        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mAddCartBtn.onClick {
            /*跨模块跳转到登录界面*/
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
        }
    }
}