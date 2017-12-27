package com.lc.user.service.impl

import com.lc.base.ext.convert
import com.lc.base.ext.convertBoolean
import com.lc.user.data.protocol.UserInfo
import com.lc.user.data.repository.UserRepository
import com.lc.user.service.UserService
import rx.Observable
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    /**
     * 注册
     */
    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {
        return repository.register(mobile, pwd, verifyCode)
                .convertBoolean()
    }

    /**
     * 登录
     */
    override fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo> {
        return repository.login(mobile, pwd, pushId).convert()
    }

    /**
     * 忘记密码1
     */
    override fun forGetPwd(mobile: String, verifyCode: String): Observable<Boolean> {
        return repository.forGetPwd(mobile, verifyCode).convertBoolean()
    }

    /**
     * 忘记密码2
     */
    override fun resetPwd(mobile: String, pwd: String): Observable<Boolean> {
        return repository.resetPwd(mobile, pwd).convertBoolean()
    }

    /**
     * 用户信息修改
     */
    override fun editUser(userIcon: String, userName: String, userGender: String, userSign: String): Observable<UserInfo> {
        return repository.editUser(userIcon, userName, userGender, userSign).convert()
    }

}