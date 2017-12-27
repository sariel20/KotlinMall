package com.lc.mall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.utils.GlideUtils
import com.lc.base.common.BaseConstant
import com.lc.base.ext.loadUrl
import com.lc.base.ext.onClick
import com.lc.base.ui.fragment.BaseFragment
import com.lc.base.utils.AppPrefsUtils
import com.lc.mall.R
import com.lc.mall.ui.activity.SettingActivity
import com.lc.provider.common.ProviderConstant
import com.lc.provider.common.isLogined
import com.lc.user.ui.activity.LoginActivity
import com.lc.user.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by LiangCheng on 2017/12/27.
 */
class MeFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_me, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mUserLogin.onClick(this)
        mSettingTv.onClick(this)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        //是否登陆
        if (isLogined()) {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
        } else {
            //登陆加载用户信息
            val userIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
            if (userIcon.isNotEmpty())
                mUserIconIv.loadUrl(userIcon)

            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mUserLogin ->
                if (isLogined())
                    startActivity<LoginActivity>()
                else
                    startActivity<UserInfoActivity>()
            R.id.mSettingTv ->
                startActivity<SettingActivity>()
        }
    }

}