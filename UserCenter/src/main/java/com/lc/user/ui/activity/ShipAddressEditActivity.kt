package com.lc.user.ui.activity

import android.os.Bundle
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.order.data.protocol.ShipAddress
import com.lc.user.R
import com.lc.user.common.OrderConstant
import com.lc.user.injection.component.DaggerShipAddressComponent
import com.lc.user.injection.module.ShipAddressModule
import com.lc.user.presenter.EditShipAddressPresenter
import com.lc.user.presenter.view.EditShipAddressView
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.jetbrains.anko.toast

/**
 * Created by LiangCheng on 2018/1/3.
 */
class ShipAddressEditActivity : BaseMvpActivity<EditShipAddressPresenter>(), EditShipAddressView {

    private var mAddress: ShipAddress? = null

    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(activityComponent)
                .shipAddressModule(ShipAddressModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        initView()
        initData()
    }

    private fun initData() {
        mAddress = intent.getParcelableExtra(OrderConstant.KEY_SHIP_ADDRESS)
        mAddress?.let {
            mHeaderBar.getTitleView().text = "修改地址"
            mShipNameEt.setText(it.shipUserName)
            mShipMobileEt.setText(it.shipUserMobile)
            mShipAddressEt.setText(it.shipAddress)
        }
    }

    private fun initView() {
        mSaveBtn.onClick {
            if (mShipNameEt.text.isNullOrEmpty()) {
                toast("请输入收货人")
                return@onClick
            }
            if (mShipMobileEt.text.isNullOrEmpty()) {
                toast("请输入联系方式")
                return@onClick
            }
            if (mShipAddressEt.text.isNullOrEmpty()) {
                toast("请输入详细地址")
                return@onClick
            }
            if (mAddress == null) {
                mPresenter.addShipAddress(
                        mShipNameEt.text.toString(),
                        mShipMobileEt.text.toString(),
                        mShipAddressEt.text.toString())
            } else {
                mAddress!!.shipUserName = mShipNameEt.text.toString()
                mAddress!!.shipUserMobile = mShipMobileEt.text.toString()
                mAddress!!.shipAddress = mShipAddressEt.text.toString()
                mPresenter.editShipAddress(mAddress!!)
            }

        }

    }

    override fun onEditShipAddressdResult(result: Boolean) {
        toast("修改成功")
        finish()
    }

    override fun onAddShipAddressdResult(result: Boolean) {
        toast("添加成功")
        finish()
    }
}