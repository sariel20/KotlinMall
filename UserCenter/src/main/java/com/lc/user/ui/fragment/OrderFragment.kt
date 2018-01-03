package com.lc.user.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennyc.view.MultiStateView
import com.lc.base.ui.fragment.BaseMvpFragment
import com.lc.user.R
import com.lc.user.common.OrderConstant
import com.lc.user.data.protocol.Order
import com.lc.user.injection.component.DaggerOrderComponent
import com.lc.user.injection.module.OrderModule
import com.lc.user.presenter.OrderListPresenter
import com.lc.user.presenter.view.OrderListView
import com.lc.user.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.fragment_order.*

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
        loadData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(activity)
        mOrderRv.adapter = mAdapter
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


}