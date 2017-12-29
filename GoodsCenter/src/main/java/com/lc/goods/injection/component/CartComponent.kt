package com.lc.goods.injection.component

import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.goods.injection.module.CartModule
import com.lc.goods.ui.fragment.CartFragment
import dagger.Component

/*
    购物车Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(CartModule::class))
interface CartComponent {
    fun inject(fragment:CartFragment)
}