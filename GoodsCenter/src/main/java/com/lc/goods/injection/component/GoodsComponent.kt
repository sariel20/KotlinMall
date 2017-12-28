package com.lc.goods.injection.component

import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.goods.injection.module.GoodsModule
import com.lc.goods.ui.activity.GoodsActivity
import com.lc.goods.ui.fragment.GoodsDetailTabOneFragment
import com.lc.goods.ui.fragment.GoodsDetailTabTwoFragment
import dagger.Component

/*
    商品Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(GoodsModule::class))
interface GoodsComponent {
    fun inject(activity: GoodsActivity)
    fun inject(fragment: GoodsDetailTabOneFragment)
    fun inject(fragment: GoodsDetailTabTwoFragment)
}
