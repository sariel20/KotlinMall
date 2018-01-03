package com.lc.user.service

import com.lc.order.data.protocol.ShipAddress
import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface ShipAddressService {
    /*
         添加收货地址
      */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String):
            Observable<Boolean>

    /*
        获取收货地址列表
     */
    fun getShipAddressList(): Observable<MutableList<ShipAddress>?>
}