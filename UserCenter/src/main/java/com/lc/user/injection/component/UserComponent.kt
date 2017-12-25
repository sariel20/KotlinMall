package com.lc.user.injection.component

import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.user.injection.module.UserModule
import com.lc.user.ui.activity.ForGetPwdActivity
import com.lc.user.ui.activity.LoginActivity
import com.lc.user.ui.activity.RegisterActivity
import com.lc.user.ui.activity.ResetPwdActivity
import dagger.Component

/**
 * Created by LiangCheng on 2017/12/20.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class)
        , modules = arrayOf(UserModule::class))
interface UserComponent {
    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ForGetPwdActivity)
    fun inject(activity: ResetPwdActivity)
}