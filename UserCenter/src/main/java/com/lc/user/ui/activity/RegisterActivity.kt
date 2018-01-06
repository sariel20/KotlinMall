package com.lc.user.ui.activity

import android.os.Bundle
import android.view.View
import com.lc.base.ext.enable
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.user.R
import com.lc.user.injection.component.DaggerUserComponent
import com.lc.user.injection.module.UserModule
import com.lc.user.presenter.RegisterPresenter
import com.lc.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * 注册
 */
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView, View.OnClickListener {

    private var pressTime: Long = 0

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     * 注册回调
     */
    override fun onRegisterResult(result: String) {
        toast(result)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mRegisterBtn.enable(mMobileEt, { isBtnEnable() })
        mRegisterBtn.enable(mVerifyCodeEt, { isBtnEnable() })
        mRegisterBtn.enable(mPwdEt, { isBtnEnable() })
        mRegisterBtn.enable(mPwdConfirmEt, { isBtnEnable() })

        mVerifyCodeBtn.onClick(this)
        mRegisterBtn.onClick(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.mRegisterBtn -> {
                if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
                    toast("两次密码输入不一致")
                    return
                }
                mPresenter.register(mMobileEt.text.toString(), mPwdEt.text.toString(), mVerifyCodeEt.text.toString())
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
                mVerifyCodeEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}
