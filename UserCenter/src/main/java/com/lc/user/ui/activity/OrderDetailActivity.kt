package com.lc.user.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.utils.YuanFenConverter
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.provider.common.ProviderConstant
import com.lc.provider.router.RouterPath
import com.lc.user.R
import com.lc.user.data.protocol.Order
import com.lc.user.injection.component.DaggerOrderComponent
import com.lc.user.injection.module.OrderModule
import com.lc.user.presenter.OrderDetailPresenter
import com.lc.user.presenter.view.OrderDetailView
import com.lc.user.ui.adapter.OrderGoodsAdapter
import kotlinx.android.synthetic.main.activity_order_detail.*

/**
 * Created by ${LiangCheng} on 2018/1/2.
 */
@Route(path = RouterPath.UserCenter.PATH_ORDER_DETAIL)
class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(activityComponent)
                .orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    private lateinit var mAdapter: OrderGoodsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        initView()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /*初始化View*/
    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    /*加载订单数据*/
    private fun loadData() {
        mPresenter.getOrderById(intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1))
    }


    /*订单列表回调*/
    override fun onGetOrderByIdResult(result: Order) {
        mAdapter.setData(result.orderGoodsList)
        mShipNameTv.setContentText(result.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(result.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(result.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText(YuanFenConverter.changeF2YWithUnit(result.totalPrice))
    }

}