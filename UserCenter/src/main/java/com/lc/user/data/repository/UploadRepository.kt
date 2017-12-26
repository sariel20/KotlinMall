package com.lc.user.data.repository

import com.lc.base.data.net.RetrofitFactory
import com.lc.base.data.protocol.BaseResp
import com.lc.user.data.api.UploadApi
import rx.Observable
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class UploadRepository @Inject constructor() {
    fun getUploadToken(): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UploadApi::class.java)
                .getUploadToken()
    }

}