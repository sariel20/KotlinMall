package com.lc.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Gravity
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseActivity
import com.lc.base.utils.AppPrefsUtils
import com.lc.goods.R
import com.lc.goods.common.GoodsConstant
import com.lc.goods.event.AddCartEvent
import com.lc.goods.event.UpdateCartSizeEvent
import com.lc.goods.ui.adapter.GoodsDetailVpAdapter
import com.lc.provider.common.afterLogin
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.startActivity
import q.rorbin.badgeview.QBadgeView

/**
 * Created by LiangCheng on 2017/12/28.
 */
class GoodsDetailActivity : BaseActivity(), View.OnClickListener {

    /*购物车数量显示*/
    private lateinit var mCartBadge: QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
        initObserver()

        loadCartSize()
    }

    private fun loadCartSize() {
        setCartBadge()
    }

    /*关联viewpage 与 fragment
    *   初始化操作
    * */
    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED

        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mLeftIv.onClick(this)
        mAddCartBtn.onClick(this)

        mCartBadge = QBadgeView(this)
        mEnterCartTv.onClick{
            startActivity<CartActivity>()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLeftIv ->
                finish()
            R.id.mAddCartBtn -> {
                /*判断是否登录*/
                afterLogin { Bus.send(AddCartEvent()) }
            }
        }
    }

    /*监听加入购物车后更新红点*/
    private fun initObserver() {
        Bus.observe<UpdateCartSizeEvent>()
                .subscribe {
                    setCartBadge()
                }.registerInBus(this)
    }

    private fun setCartBadge() {
        /*小红点位置*/
        mCartBadge.badgeGravity = Gravity.TOP or Gravity.END
        /*偏移量*/
        mCartBadge.setGravityOffset(22f, -2f, true)
        /*字体大小*/
        mCartBadge.setBadgeTextSize(10f, true)
        /*绑定控件  设置值*/
        mCartBadge.bindTarget(mEnterCartTv)
                .badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}