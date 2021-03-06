package com.lc.user.injection.component

import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.user.injection.module.OrderModule
import com.lc.user.ui.activity.OrderConfirmActivity
import com.lc.user.ui.activity.OrderDetailActivity
import com.lc.user.ui.fragment.OrderFragment
import dagger.Component

/**
 * Created by LiangCheng on 2017/12/20.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class)
        , modules = arrayOf(OrderModule::class))
interface OrderComponent {
    fun inject(activity: OrderConfirmActivity)
    fun inject(activity: OrderDetailActivity)
    fun inject(fragment: OrderFragment)
}