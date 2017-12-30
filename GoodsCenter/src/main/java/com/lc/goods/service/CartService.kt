package com.lc.goods.service

import com.lc.goods.data.protocol.CartGoods
import rx.Observable

/*
    购物车 业务层 接口
 */
interface CartService {
    /*
            添加商品到购物车
         */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                goodsCount: Int, goodsSku: String): Observable<Int>

    /*
        获取购物车列表
     */
    fun getCartList(): Observable<MutableList<CartGoods>?>

    /*删除商品*/
    fun deleteCartList(list: List<Int>): Observable<Boolean>

    /*提交购物车商品*/
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int>
}