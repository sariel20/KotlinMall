package com.lc.goods.injection.module

import com.lc.goods.service.CartService
import com.lc.goods.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

/*
    购物车Module
 */
@Module
class CartModule {

    @Provides
    fun provideCartService(cartService: CartServiceImpl): CartService {
        return cartService
    }

}