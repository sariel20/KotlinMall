package com.lc.pay.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.kotlin.base.utils.YuanFenConverter
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.pay.R
import com.lc.pay.injection.component.DaggerPayComponent
import com.lc.pay.injection.module.PayModule
import com.lc.pay.presenter.PayPresenter
import com.lc.pay.presenter.view.PayView
import com.lc.provider.common.ProviderConstant
import com.lc.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * Created by LiangCheng on 2018/1/5.
 */
@Route(path = RouterPath.PaySDK.PATH_PAY)
class CashRegisterActivity : BaseMvpActivity<PayPresenter>(), PayView {

    override fun injectComponent() {
        DaggerPayComponent.builder().activityComponent(activityComponent)
                .payModule(PayModule()).build().inject(this)
        mPresenter.mView = this
    }

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0

    @Autowired(name = ProviderConstant.KEY_ORDER_PRICE)
    @JvmField
    var mOrderPrice: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)
        /*支付沙箱环境配置*/
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)

        ARouter.getInstance().inject(this)

        initView()

    }

    private fun initView() {
        mTotalPriceTv.text = YuanFenConverter.changeF2YWithUnit(mOrderPrice)

        mAlipayTypeTv.isSelected = true
        mAlipayTypeTv.onClick {
            updatePayType(true, false)
        }

        mWeixinTypeTv.onClick {
            updatePayType(false, true)
        }

        mPayBtn.onClick {
            mPresenter.getPaySign(mOrderId, mOrderPrice)
        }
    }

    private fun updatePayType(isAliPay: Boolean, isWx: Boolean) {
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWx
    }

    /*获取支付签名*/
    override fun onGetPaySignResult(result: String) {
        doAsync {
            val resultMap: Map<String, String> =
                    PayTask(this@CashRegisterActivity).payV2(result, true)
            uiThread {
                if (resultMap["resultStatus"].equals("9000")) {
                    /*支付成功，刷新订单状态*/
                    mPresenter.payOrder(mOrderId)
                } else {
                    toast("支付失败${resultMap["memo"]}")
                }
            }
        }
    }

    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")
        finish()
    }

}