package com.lc.user.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lc.base.ext.onClick
import com.lc.base.ext.startLoading
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.order.data.protocol.ShipAddress
import com.lc.user.R
import com.lc.user.common.OrderConstant
import com.lc.user.event.SelectAddressEvent
import com.lc.user.injection.component.DaggerShipAddressComponent
import com.lc.user.injection.module.ShipAddressModule
import com.lc.user.presenter.ShipAddressListPresenter
import com.lc.user.presenter.view.ShipAddressListView
import com.lc.user.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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
                mPresenter.setDefaultShipAddress(address)
            }

            /*编辑*/
            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(
                        OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            /*删除*/
            override fun onDelete(address: ShipAddress) {
                showAlertView(address.id)
            }
        }

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress> {
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                finish()
            }
        })

        /*新增地址*/
        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }
    }

    fun showAlertView(id: Int) {
        AlertView("确认删除该地址吗？", null, "取消", null,
                arrayOf("删除"), this,
                AlertView.Style.Alert,
                OnItemClickListener { _, position ->
                    when (position) {
                        0 -> {
                            mPresenter.deleteShipAddress(id)
                        }
                    }
                }).show()
    }

    override fun onSetDefaultResult(result: Boolean) {
        toast("设置成功")
        loadData()
    }

    override fun onDeleteAddressResult(result: Boolean) {
        toast("删除成功")
        loadData()
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