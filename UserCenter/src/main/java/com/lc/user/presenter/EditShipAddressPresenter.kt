package com.lc.user.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.user.presenter.view.EditShipAddressView
import com.lc.user.service.ShipAddressService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class EditShipAddressPresenter @Inject constructor() : BasePresenter<EditShipAddressView>() {
    @Inject
    lateinit var shipAddressService: ShipAddressService

    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        shipAddressService.addShipAddress(shipUserName, shipUserMobile, shipAddress)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onAddShipAddressdResult(t)
                    }
                }, lifecycleProvider)

    }
}