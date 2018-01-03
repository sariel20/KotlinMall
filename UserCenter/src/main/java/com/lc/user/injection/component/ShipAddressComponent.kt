package com.lc.user.injection.component

import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.user.injection.module.ShipAddressModule
import com.lc.user.ui.activity.ShipAddressActivity
import com.lc.user.ui.activity.ShipAddressEditActivity
import dagger.Component

/**
 * Created by LiangCheng on 2017/12/20.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class)
        , modules = arrayOf(ShipAddressModule::class))
interface ShipAddressComponent {
    fun inject(activity: ShipAddressEditActivity)
    fun inject(activity: ShipAddressActivity)
}