package com.lc.user.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.user.data.protocol.UserInfo

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface LoginView : BaseView {

    fun onLoginResult(result: UserInfo)
}