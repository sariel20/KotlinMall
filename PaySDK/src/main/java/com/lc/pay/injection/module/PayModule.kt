package com.lc.pay.injection.module

import com.lc.pay.service.PayService
import com.lc.pay.service.impl.PayServiceImpl
import dagger.Module
import dagger.Provides

/*
    支付Module
 */
@Module
class PayModule {

    @Provides
    fun provideCartService(payService: PayServiceImpl): PayService {
        return payService
    }

}