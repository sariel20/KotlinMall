package com.lc.user.injection.module

import com.lc.user.service.OrderService
import com.lc.user.service.impl.OrderServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Module
class OrderModule {

    @Provides
    fun providesOrderService(serviceImpl: OrderServiceImpl): OrderService {
        return serviceImpl
    }

}