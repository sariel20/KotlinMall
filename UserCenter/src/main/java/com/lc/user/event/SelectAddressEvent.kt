package com.lc.user.event

import com.lc.order.data.protocol.ShipAddress


/*
    选择收货人信息事件
 */
class SelectAddressEvent(val address: ShipAddress)
