package com.lc.message.injection.component


import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.message.injection.module.MessageModule
import com.lc.message.ui.fragment.MessageFragment
import dagger.Component

/*
    消息模块注入组件
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(MessageModule::class))
interface MessageComponent {
    fun inject(fragment: MessageFragment)
}
