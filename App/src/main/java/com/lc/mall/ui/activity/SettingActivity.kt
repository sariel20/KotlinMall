package com.lc.mall.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.user.utils.UserPrefsUtils
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseActivity
import com.lc.mall.R
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by 18480 on 2017/12/27.
 */
class SettingActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initView()
    }

    private fun initView() {
        mLogoutBtn.onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLogoutBtn -> {
                UserPrefsUtils.putUserInfo(null)
                finish()
            }
        }
    }

}