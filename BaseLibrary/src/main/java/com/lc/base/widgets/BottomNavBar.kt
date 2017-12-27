package com.lc.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.lc.base.R

/**
 * Created by LiangCheng on 2017/12/27.
 */
class BottomNavBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    private val mCartBadge: TextBadgeItem//带数字红点
    private val mMsgBadge: ShapeBadgeItem//纯红点

    init {
        //首页
        val homeItem = BottomNavigationItem(
                R.drawable.btn_nav_home_press, resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.btn_nav_home_normal)
                .setActiveColorResource(R.color.common_blue)//选中
                .setInActiveColorResource(R.color.text_normal)//未选中

        //分类
        val categoryItem = BottomNavigationItem(
                R.drawable.btn_nav_category_press, resources.getString(R.string.nav_bar_category))
                .setInactiveIconResource(R.drawable.btn_nav_category_normal)
                .setActiveColorResource(R.color.common_blue)//选中
                .setInActiveColorResource(R.color.text_normal)//未选中

        //购物车
        val cartItem = BottomNavigationItem(
                R.drawable.btn_nav_cart_press, resources.getString(R.string.nav_bar_cart))
                .setInactiveIconResource(R.drawable.btn_nav_cart_normal)
                .setActiveColorResource(R.color.common_blue)//选中
                .setInActiveColorResource(R.color.text_normal)//未选中

        mCartBadge = TextBadgeItem()
        cartItem.setBadgeItem(mCartBadge)

        //消息
        val msgItem = BottomNavigationItem(
                R.drawable.btn_nav_msg_press, resources.getString(R.string.nav_bar_msg))
                .setInactiveIconResource(R.drawable.btn_nav_msg_normal)
                .setActiveColorResource(R.color.common_blue)//选中
                .setInActiveColorResource(R.color.text_normal)//未选中

        mMsgBadge = ShapeBadgeItem()
        msgItem.setBadgeItem(mMsgBadge)
        mMsgBadge.setShape(ShapeBadgeItem.SHAPE_OVAL)//圆形

        //我的
        val userItem = BottomNavigationItem(
                R.drawable.btn_nav_user_press, resources.getString(R.string.nav_bar_user))
                .setInactiveIconResource(R.drawable.btn_nav_user_normal)
                .setActiveColorResource(R.color.common_blue)//选中
                .setInActiveColorResource(R.color.text_normal)//未选中


        setMode(BottomNavigationBar.MODE_FIXED)//模式
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//样式
        setBarBackgroundColor(R.color.common_white)

        addItem(homeItem)
                .addItem(categoryItem)
                .addItem(cartItem)
                .addItem(msgItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0)//默认选中
                .initialise()//初始化方法
    }

    fun checkCartBadge(count: Int) {
        if (count == 0) {
            mCartBadge.hide()
        } else {
            mCartBadge.show()
            mCartBadge.setText("$count")
        }
    }

    fun checkMsgBadge(isVis: Boolean) {
        if (isVis) {
            mMsgBadge.show()
        } else {
            mMsgBadge.hide()
        }
    }
}