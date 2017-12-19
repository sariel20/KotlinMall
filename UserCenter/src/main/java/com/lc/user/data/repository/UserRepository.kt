package com.lc.user.data.repository

import com.lc.base.data.net.RetrofitFactory
import com.lc.base.data.protocol.BaseResp
import com.lc.user.data.api.UserApi
import com.lc.user.data.protocol.RegisterReq
import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
class UserRepository {
    fun register(mobile: String, pwd: String, verifyCode: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .register(RegisterReq(mobile, pwd, verifyCode))
    }
}