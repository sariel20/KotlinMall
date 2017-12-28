package com.lc.goods.service

import com.lc.goods.data.protocol.Category
import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface CategoryService {
    fun getCategory(parentId: Int): Observable<MutableList<Category>?>
}