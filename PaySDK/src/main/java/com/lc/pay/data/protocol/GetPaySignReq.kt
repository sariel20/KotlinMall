package com.lc.pay.data.protocol

/*
    获取支付宝 支付签名
 */
data class GetPaySignReq(val orderId: Int, val totalPrice: Long)
