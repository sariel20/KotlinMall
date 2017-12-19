package com.lc.user.presenter

import com.lc.base.presenter.BasePresenter
import com.lc.user.presenter.view.RegisterView

/**
 * Created by LiangCheng on 2017/12/19.
 */
open class RegisterPresenter : BasePresenter<RegisterView>() {

    fun register(mobile: String, verifyCode: String) {
        /*
          业务逻辑
         */
        mView.onRegisterResult(true)
    }
}