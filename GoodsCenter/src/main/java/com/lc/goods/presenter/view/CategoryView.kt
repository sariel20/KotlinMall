package com.lc.goods.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.goods.data.protocol.Category

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface CategoryView : BaseView {

    fun onGetCategoryResult(result: MutableList<Category>?)
}