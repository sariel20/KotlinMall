package com.lc.order.data.protocol

import android.os.Parcelable

/*
   收货地址
 */
@Parcelize
data class ShipAddress(
        val id: Int,
        var shipUserName: String,
        var shipUserMobile: String,
        var shipAddress: String,
        var shipIsDefault: Int
) : Parcelable
