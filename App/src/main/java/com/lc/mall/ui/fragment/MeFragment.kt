package com.lc.mall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lc.base.ext.loadUrl
import com.lc.base.ext.onClick
import com.lc.base.ui.fragment.BaseFragment
import com.lc.base.utils.AppPrefsUtils
import com.lc.mall.R
import com.lc.mall.ui.activity.SettingActivity
import com.lc.provider.common.ProviderConstant
import com.lc.provider.common.afterLogin
import com.lc.provider.common.isLogined
import com.lc.user.common.OrderConstant
import com.lc.user.common.OrderStatus
import com.lc.user.ui.activity.OrderActivity
import com.lc.user.ui.activity.ShipAddressActivity
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
        mAddressTv.onClick(this)

        mAllOrderTv.onClick(this)
        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
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
                afterLogin {
                    startActivity<UserInfoActivity>()
                }
            R.id.mSettingTv ->
                afterLogin {
                    startActivity<SettingActivity>()
                }
            R.id.mAddressTv ->
                afterLogin {
                    startActivity<ShipAddressActivity>()
                }

        /*全部订单*/
            R.id.mAllOrderTv -> {
                afterLogin {
                    startActivity<OrderActivity>()
                }
            }
        /*待付款*/
            R.id.mWaitPayOrderTv -> {
                afterLogin {
                    startActivity<OrderActivity>(
                            OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
                }
            }
        /*待收货*/
            R.id.mWaitConfirmOrderTv -> {
                afterLogin {
                    startActivity<OrderActivity>(
                            OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
                }
            }
        /*已完成*/
            R.id.mCompleteOrderTv -> {
                afterLogin {
                    startActivity<OrderActivity>(
                            OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
                }
            }

        }
    }

}