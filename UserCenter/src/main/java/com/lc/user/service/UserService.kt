package com.lc.user.service

import com.lc.user.data.protocol.UserInfo
import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface UserService {
    fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean>

    fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo>

    fun forGetPwd(mobile: String, verifyCode: String): Observable<Boolean>

    fun resetPwd(mobile: String, pwd: String): Observable<Boolean>

    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String): Observable<UserInfo>
}