package com.lc.user.ui.activity

import android.os.Bundle
import android.view.View
import com.lc.base.ext.enable
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.user.R
import com.lc.user.injection.component.DaggerUserComponent
import com.lc.user.injection.module.UserModule
import com.lc.user.presenter.ForGetPwdPresenter
import com.lc.user.presenter.view.ForGetPwdView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 忘记密码1
 */
class ForGetPwdActivity : BaseMvpActivity<ForGetPwdPresenter>(), ForGetPwdView, View.OnClickListener {
    override fun onForGetPwdResult(result: String) {
        startActivity<ResetPwdActivity>("mobile" to mMobileEt.text.toString())
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)

        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mNextBtn.enable(mMobileEt, { isBtnEnable() })
        mNextBtn.enable(mVerifyCodeEt, { isBtnEnable() })

        mNextBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.mNextBtn -> {
                mPresenter.forGetPwd(mMobileEt.text.toString(), mVerifyCodeEt.text.toString())
            }
            R.id.mVerifyCodeBtn -> {
                if (!mMobileEt.text.isNotEmpty()) {
                    toast("请输入手机号码")
                    return
                }
                mVerifyCodeEt.setText("123456")
                mVerifyCodeBtn.requestSendVerifyNumber()

            }
        }
    }

    /**
     * btn是否可以点击
     */
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not()
    }
}