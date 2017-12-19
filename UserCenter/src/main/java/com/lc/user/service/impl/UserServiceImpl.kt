package com.lc.user.service.impl

import com.lc.base.data.protocol.BaseResp
import com.lc.base.rx.BaseException
import com.lc.user.data.repository.UserRepository
import com.lc.user.service.UserService
import rx.Observable
import rx.functions.Func1

/**
 * Created by LiangCheng on 2017/12/19.
 */
class UserServiceImpl : UserService {
    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {
        val repository = UserRepository()
        return repository.register(mobile, pwd, verifyCode).flatMap(object : Func1<BaseResp<String>, Observable<Boolean>> {

            override fun call(t: BaseResp<String>): Observable<Boolean> {
                if (t.stats != 0) {
                    return Observable.error(BaseException(t.stats, t.message))
                }
                return Observable.just(true)
            }
        })
    }
}