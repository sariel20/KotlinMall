package com.lc.goods.service.impl

import com.lc.base.ext.convert
import com.lc.goods.data.protocol.CartGoods
import com.lc.goods.data.repository.CartRepository
import com.lc.goods.service.CartService
import rx.Observable
import javax.inject.Inject

/*
    购物车 业务层 实现类
 */
class CartServiceImpl @Inject constructor() : CartService {

    @Inject
    lateinit var repository: CartRepository

    override fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long, goodsCount: Int, goodsSku: String): Observable<Int> {
        return repository.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku).convert()
    }

    override fun getCartList(): Observable<MutableList<CartGoods>?> {
        return repository.getCartList().convert()
    }

}