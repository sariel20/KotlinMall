package com.lc.goods.injection.component

import com.lc.base.injection.PerComponentScope
import com.lc.base.injection.component.ActivityComponent
import com.lc.goods.injection.module.CategoryModule
import com.lc.goods.ui.fragment.CategoryFragment
import dagger.Component

/**
 * Created by LiangCheng on 2017/12/20.
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class)
        , modules = arrayOf(CategoryModule::class))
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}