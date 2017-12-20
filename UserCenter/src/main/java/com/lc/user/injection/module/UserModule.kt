package com.lc.user.injection.module

import com.lc.user.service.UserService
import com.lc.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Module
class UserModule{

    @Provides
    fun providesUserService(serviceImpl: UserServiceImpl):UserService{
        return serviceImpl
    }

}