package com.lc.user.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.lc.base.ext.onClick
import com.lc.base.ext.startLoading
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.order.data.protocol.ShipAddress
import com.lc.user.R
import com.lc.user.injection.component.DaggerShipAddressComponent
import com.lc.user.injection.module.ShipAddressModule
import com.lc.user.presenter.ShipAddressListPresenter
import com.lc.user.presenter.view.ShipAddressListView
import com.lc.user.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity

/**
 * Created by LiangCheng on 2018/1/3.
 */
class ShipAddressActivity : BaseMvpActivity<ShipAddressListPresenter>(), ShipAddressListView {
    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(activityComponent)
                .shipAddressModule(ShipAddressModule()).build().inject(this)
        mPresenter.mView = this
    }

    private lateinit var mAdapter: ShipAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getShipAddressList()
    }

    private fun initView() {
        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter

        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            /*设置默认*/
            override fun onSetDefault(address: ShipAddress) {

            }

            /*编辑*/
            override fun onEdit(address: ShipAddress) {

            }

            /*删除*/
            override fun onDelete(address: ShipAddress) {

            }
        }

        /*新增地址*/
        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }
    }

    /*地址列表回调*/
    override fun onGetShipAddressdListResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            mAdapter.setData(result!!)
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }
}