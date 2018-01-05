package com.lc.pay.injection.component

import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.pay.injection.module.PayModule
import com.lc.pay.ui.activity.CashRegisterActivity
import dagger.Component

/*
    支付Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(PayModule::class))
interface PayComponent {
    fun inject(activity: CashRegisterActivity)
}