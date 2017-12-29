package com.lc.goods.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.base.utils.AppPrefsUtils
import com.lc.goods.common.GoodsConstant
import com.lc.goods.data.protocol.Goods
import com.lc.goods.presenter.view.GoodsDetailView
import com.lc.goods.service.CartService
import com.lc.goods.service.GoodsService
import javax.inject.Inject

/*
    商品详情 Presenter
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsService
    @Inject
    lateinit var cartService: CartService

    /*
        获取商品详情
     */
    fun getGoodsDetail(parentId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        goodsService.getGoodsDetail(parentId)
                .execute(object : BaseSubscriber<Goods>(mView) {
                    override fun onNext(t: Goods) {
                        mView.onGetGoodsDetailResult(t)
                    }
                }, lifecycleProvider)

    }

    /*加入购物车*/
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                goodsCount: Int, goodsSku: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        cartService.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
                .execute(object : BaseSubscriber<Int>(mView) {
                    override fun onNext(t: Int) {
                        /*存放返回购物车数量*/
                        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, t)
                        mView.onAddCartResult(t)
                    }
                }, lifecycleProvider)

    }

}