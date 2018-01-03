package com.lc.user.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.utils.YuanFenConverter
import com.lc.base.ext.onClick
import com.lc.base.ext.setVis
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.provider.common.ProviderConstant
import com.lc.provider.router.RouterPath
import com.lc.user.R
import com.lc.user.data.protocol.Order
import com.lc.user.event.SelectAddressEvent
import com.lc.user.injection.component.DaggerOrderComponent
import com.lc.user.injection.module.OrderModule
import com.lc.user.presenter.OrderConfirmPresenter
import com.lc.user.presenter.view.OrderConfirmView
import com.lc.user.ui.adapter.OrderGoodsAdapter
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by ${LiangCheng} on 2018/1/2.
 */
@Route(path = RouterPath.UserCenter.PATH_ORDER)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView, View.OnClickListener {

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(activityComponent)
                .orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    /*ARoute传递数据*/
    /*mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)*/
    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId = 0

    private lateinit var mAdapter: OrderGoodsAdapter

    private var mCurrentOrder: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        loadData()

        initObServer()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /*初始化View*/
    private fun initView() {
        mSelectShipTv.onClick(this)
        mShipView.onClick(this)
        mSubmitOrderBtn.onClick(this)

        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    /*加载订单数据*/
    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    /*选择地址监听*/
    private fun initObServer() {
        Bus.observe<SelectAddressEvent>()
                .subscribe { t: SelectAddressEvent ->
                    run {
                        /*更新订单中的地址*/
                        mCurrentOrder?.let {
                            it.shipAddress = t.address
                        }
                        updateAddressView()
                    }
                }
                .registerInBus(this)
    }


    /*订单列表回调*/
    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "总价：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"

        updateAddressView()
    }

    /*更新地址*/
    private fun updateAddressView() {
        mCurrentOrder?.let {
            if (it.shipAddress == null) {
                mSelectShipTv.setVis(true)
                mShipView.setVis(false)
            } else {
                mSelectShipTv.setVis(false)
                mShipView.setVis(true)

                mShipNameTv.text = "${it.shipAddress!!.shipUserName}   ${it.shipAddress!!.shipUserMobile}"
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }

    }

    /*提交订单回调*/
    override fun onSubmitOrderResult(result: Boolean) {
        toast("提交成功")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mSelectShipTv -> {
                startActivity<ShipAddressActivity>()
            }
            R.id.mShipView -> {
                startActivity<ShipAddressActivity>()
            }
            R.id.mSubmitOrderBtn -> {
                mCurrentOrder?.let {
                    mPresenter.submitOrder(mCurrentOrder!!)
                }
            }
        }
    }
}