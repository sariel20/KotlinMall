package com.lc.user.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.user.data.protocol.UserInfo
import com.lc.user.presenter.view.UserInfoView
import com.lc.user.service.UploadService
import com.lc.user.service.UserService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {
    @Inject
    lateinit var userInfoService: UserService
    @Inject
    lateinit var uploadService: UploadService

    fun getUploadToken() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        uploadService.getUploadToken().execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetUploadTokenResult(t)
            }
        }, lifecycleProvider)
    }

    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userInfoService.editUser(userIcon, userName, userGender, userSign)
                .execute(object : BaseSubscriber<UserInfo>(mView) {
                    override fun onNext(t: UserInfo) {
                    }
                }, lifecycleProvider)
    }
}