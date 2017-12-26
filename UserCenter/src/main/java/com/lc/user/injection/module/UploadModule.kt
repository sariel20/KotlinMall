package com.lc.user.injection.module

import com.lc.user.service.UploadService
import com.lc.user.service.impl.UploadServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by LiangCheng on 2017/12/20.
 */
@Module
class UploadModule {

    @Provides
    fun providesUploadService(uploadService: UploadServiceImpl): UploadService {
        return uploadService
    }

}