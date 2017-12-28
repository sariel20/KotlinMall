package com.lc.goods.injection.module

import com.lc.goods.service.CategoryService
import com.lc.goods.service.impl.CategoryServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Module
class CategoryModule {

    @Provides
    fun providesUserService(categoryService: CategoryServiceImpl): CategoryService {
        return categoryService
    }

}