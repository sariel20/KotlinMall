package com.lc.user.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.user.presenter.view.RegisterView
import com.lc.user.service.impl.UserServiceImpl

/**
 * Created by LiangCheng on 2017/12/19.
 */
open class RegisterPresenter : BasePresenter<RegisterView>() {

    fun register(mobile: String, pwd: String, verifyCode: String) {
        /*
          业务逻辑
         */
        val userService = UserServiceImpl()
        userService.register(mobile, pwd, verifyCode)
                .execute(object : BaseSubscriber<Boolean>() {
                    override fun onNext(t: Boolean) {
                        mView.onRegisterResult(t)
                    }
                })

    }

}