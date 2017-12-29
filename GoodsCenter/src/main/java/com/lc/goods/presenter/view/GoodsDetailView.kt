package com.lc.goods.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.goods.data.protocol.Goods

/*
    商品详情 视图回调
 */
interface GoodsDetailView : BaseView {

    //获取商品详情
    fun onGetGoodsDetailResult(result: Goods)

    /*添加购物车*/
    fun onAddCartResult(result: Int)
}