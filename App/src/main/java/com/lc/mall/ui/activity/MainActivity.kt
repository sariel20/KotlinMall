package com.lc.mall.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.lc.base.common.AppManager
import com.lc.base.ui.activity.BaseActivity
import com.lc.base.utils.AppPrefsUtils
import com.lc.goods.common.GoodsConstant
import com.lc.goods.event.UpdateCartSizeEvent
import com.lc.goods.ui.fragment.CartFragment
import com.lc.goods.ui.fragment.CategoryFragment
import com.lc.mall.R
import com.lc.mall.ui.fragment.HomeFragment
import com.lc.mall.ui.fragment.MeFragment
import com.lc.message.ui.fragment.MessageFragment
import com.lc.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : BaseActivity() {

    private val mStack = Stack<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { CategoryFragment() }
    private val mCartFragment by lazy { CartFragment() }
    private val mMsgFragment by lazy { MessageFragment() }
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        initBottomNav()

        changeFragment(0)

        initObserver()

        loadCartSize()
    }

    private fun initObserver() {
        /*监听加入购物车后更新红点*/
        Bus.observe<UpdateCartSizeEvent>()
                .subscribe {
                    loadCartSize()
                }.registerInBus(this)

        /*监听有新消息显示红点*/
        Bus.observe<MessageBadgeEvent>()
                .subscribe { t: MessageBadgeEvent ->
                    run {
                        mBottomNavBar.checkMsgBadge(t.isVisible)
                    }

                }.registerInBus(this)
    }

    private fun loadCartSize() {
        mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContaier, mHomeFragment)
        manager.add(R.id.mContaier, mCategoryFragment)
        manager.add(R.id.mContaier, mCartFragment)
        manager.add(R.id.mContaier, mMsgFragment)
        manager.add(R.id.mContaier, mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }

    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }

        })

        mBottomNavBar.checkMsgBadge(false)
    }

    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragments in mStack) {
            manager.hide(fragments)
        }
        manager.show(mStack[position])
        manager.commit()
    }

    private var pressTime: Long = 0
    override fun onBackPressed() {
        super.onBackPressed()
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }
}

