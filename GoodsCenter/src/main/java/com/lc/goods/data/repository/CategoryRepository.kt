package com.lc.goods.data.repository

import com.lc.base.data.net.RetrofitFactory
import com.lc.base.data.protocol.BaseResp
import com.lc.goods.data.api.CategoryApi
import com.lc.goods.data.protocol.Category
import com.lc.goods.data.protocol.GetCategoryReq
import rx.Observable
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class CategoryRepository @Inject constructor() {
    fun getCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.instance.create(CategoryApi::class.java)
                .getCategory(GetCategoryReq(parentId))
    }
}