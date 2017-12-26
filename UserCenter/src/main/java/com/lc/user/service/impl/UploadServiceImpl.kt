package com.lc.user.service.impl

import com.lc.base.ext.convert
import com.lc.user.data.repository.UploadRepository
import com.lc.user.service.UploadService
import rx.Observable
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class UploadServiceImpl @Inject constructor() : UploadService {

    @Inject
    lateinit var repository: UploadRepository

    override fun getUploadToken(): Observable<String> {
        return repository.getUploadToken().convert()
    }


}