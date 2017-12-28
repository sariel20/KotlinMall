package com.lc.goods.service.impl

import com.lc.base.ext.convert
import com.lc.goods.data.protocol.Category
import com.lc.goods.data.repository.CategoryRepository
import com.lc.goods.service.CategoryService
import rx.Observable
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class CategoryServiceImpl @Inject constructor() : CategoryService {
    @Inject
    lateinit var repository: CategoryRepository

    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> {
        return repository.getCategory(parentId).convert()
    }

}