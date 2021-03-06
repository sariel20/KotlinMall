package com.lc.user.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.order.data.protocol.ShipAddress
import com.lc.user.presenter.view.ShipAddressListView
import com.lc.user.service.ShipAddressService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class ShipAddressListPresenter @Inject constructor() : BasePresenter<ShipAddressListView>() {
    @Inject
    lateinit var shipAddressService: ShipAddressService

    fun getShipAddressList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        shipAddressService.getShipAddressList()
                .execute(object : BaseSubscriber<MutableList<ShipAddress>?>(mView) {
                    override fun onNext(t: MutableList<ShipAddress>?) {
                        mView.onGetShipAddressdListResult(t)
                    }
                }, lifecycleProvider)
    }

    fun setDefaultShipAddress(shipAddress: ShipAddress) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        shipAddressService.editShipAddress(shipAddress)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onSetDefaultResult(t)
                    }
                }, lifecycleProvider)
    }

    fun deleteShipAddress(id: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        shipAddressService.deleteShipAddress(id)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onDeleteAddressResult(t)
                    }
                }, lifecycleProvider)
    }
}