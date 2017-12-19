package com.lc.user.service

import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface UserService {
    fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean>
}