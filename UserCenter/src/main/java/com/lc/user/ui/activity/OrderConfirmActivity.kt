package com.lc.user.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.utils.YuanFenConverter
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.provider.common.ProviderConstant
import com.lc.provider.router.RouterPath
import com.lc.user.R
import com.lc.user.data.protocol.Order
import com.lc.user.injection.component.DaggerOrderComponent
import com.lc.user.injection.module.OrderModule
import com.lc.user.presenter.OrderConfirmPresenter
import com.lc.user.presenter.view.OrderConfirmView
import com.lc.user.ui.adapter.OrderGoodsAdapter
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        loadData()
    }

    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    private fun initView() {
        mSelectShipTv.onClick(this)

        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    override fun onGetOrderByIdResult(result: Order) {
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "总价：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mSelectShipTv -> {
                startActivity<ShipAddressActivity>()
            }
        }
    }
}