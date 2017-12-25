package com.lc.user.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.user.presenter.view.ForGetPwdView
import com.lc.user.service.UserService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class ForGetPwdPresenter @Inject constructor() : BasePresenter<ForGetPwdView>() {
    @Inject
    lateinit var userService: UserService

    fun forGetPwd(mobile: String, verifyCode: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.forGetPwd(mobile, verifyCode)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        if (t)
                            mView.onForGetPwdResult("验证成功")
                    }
                }, lifecycleProvider)

    }


}