package com.lc.user.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.order.data.protocol.ShipAddress

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface ShipAddressListView : BaseView {

    fun onGetShipAddressdListResult(result: MutableList<ShipAddress>?)
}