package com.lc.user.ui.activity

import android.os.Bundle
import android.view.View
import com.lc.base.ext.enable
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.user.R
import com.lc.user.data.protocol.UserInfo
import com.lc.user.injection.component.DaggerUserComponent
import com.lc.user.injection.module.UserModule
import com.lc.user.presenter.LoginPresenter
import com.lc.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

/**
 * 登录
 */
    class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

        override fun injectComponent() {
            DaggerUserComponent.builder().activityComponent(activityComponent)
                    .userModule(UserModule()).build().inject(this)
            mPresenter.mView = this
        }

        override fun onLoginResult(result: UserInfo) {
            startActivity<UserInfoActivity>()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            initView()
        }

        /**
         * 初始化视图
         */
        private fun initView() {
            mHeaderBar.getRightView().onClick(this)

            mLoginBtn.enable(mMobileEt, { isBtnEnable() })
            mLoginBtn.enable(mPwdEt, { isBtnEnable() })

            mLoginBtn.onClick(this)
            mForgetPwdTv.onClick(this)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.mLoginBtn -> {
                    mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(), "")
                }
                R.id.mForgetPwdTv -> {
                    startActivity<ForGetPwdActivity>()
                }
                R.id.mRightTv -> {
                    startActivity<RegisterActivity>()
                }
            }
        }

        /**
         * btn是否可以点击
         */
        private fun isBtnEnable(): Boolean {
            return mMobileEt.text.isNullOrEmpty().not() &&
                    mPwdEt.text.isNullOrEmpty().not()
        }
    }