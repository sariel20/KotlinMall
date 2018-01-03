package com.lc.user.injection.module

import com.lc.user.service.ShipAddressService
import com.lc.user.service.impl.ShipAddressServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Module
class ShipAddressModule {

    @Provides
    fun providesShipAddressService(serviceImpl: ShipAddressServiceImpl): ShipAddressService {
        return serviceImpl
    }

}