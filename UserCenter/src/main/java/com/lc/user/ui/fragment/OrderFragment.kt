package com.lc.user.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lc.base.ui.fragment.BaseMvpFragment
import com.lc.provider.common.ProviderConstant
import com.lc.provider.router.RouterPath
import com.lc.user.R
import com.lc.user.common.OrderConstant
import com.lc.user.data.protocol.Order
import com.lc.user.injection.component.DaggerOrderComponent
import com.lc.user.injection.module.OrderModule
import com.lc.user.presenter.OrderListPresenter
import com.lc.user.presenter.view.OrderListView
import com.lc.user.ui.activity.OrderDetailActivity
import com.lc.user.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by LiangCheng on 2018/1/3.
 */
class OrderFragment : BaseMvpFragment<OrderListPresenter>(), OrderListView {

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(activityComponent)
                .orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    private lateinit var mAdapter: OrderAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(activity)
        mOrderRv.adapter = mAdapter

        mAdapter.listener = object : OrderAdapter.OnOptClickListener {
            override fun onOptClick(optType: Int, order: Order) {
                when (optType) {
                /*确认收货*/
                    OrderConstant.OPT_ORDER_CONFIRM -> {
                        mPresenter.confirmOrder(order.id)
                    }
                /*订单付款*/
                    OrderConstant.OPT_ORDER_PAY -> {
                        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                                .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
                                .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
                                .navigation()
                    }
                /*取消订单*/
                    OrderConstant.OPT_ORDER_CANCEL -> {
                        showAlertView(order.id)
                    }
                }
            }
        }

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Order> {
            override fun onItemClick(item: Order, position: Int) {
                startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
            }
        })
    }

    private fun loadData() {
        mPresenter.getOrderList(arguments.getInt(OrderConstant.KEY_ORDER_STATUS, -1))
    }

    override fun onGetOrderListResult(result: MutableList<Order>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT//加载完成后改变视图状态
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onConfirmOrderResult(result: Boolean) {
        loadData()
    }

    override fun onCancelOrderResult(result: Boolean) {
        loadData()
    }

    fun showAlertView(id: Int) {
        AlertView("确认取消该订单吗？", null, "取消", null,
                arrayOf("确认"), activity,
                AlertView.Style.Alert,
                OnItemClickListener { _, position ->
                    when (position) {
                        0 -> {
                            mPresenter.cancelOrder(id)
                        }
                    }
                }).show()
    }


}