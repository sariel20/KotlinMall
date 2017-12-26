package com.lc.user.presenter.view

import com.lc.base.presenter.view.BaseView

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface UserInfoView : BaseView {

    fun onGetUploadTokenResult(result: String)
}