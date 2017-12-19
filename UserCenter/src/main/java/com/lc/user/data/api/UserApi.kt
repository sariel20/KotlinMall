package com.lc.user.data.api

import com.lc.base.data.protocol.BaseResp
import com.lc.user.data.protocol.RegisterReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface UserApi {
    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

}