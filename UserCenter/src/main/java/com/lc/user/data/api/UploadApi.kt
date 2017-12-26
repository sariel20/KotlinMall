package com.lc.user.data.api

import com.lc.base.data.protocol.BaseResp
import retrofit2.http.POST
import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface UploadApi {
    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>

}