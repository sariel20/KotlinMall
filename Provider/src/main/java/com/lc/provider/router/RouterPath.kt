package com.lc.provider.router

/**
 * Created by LiangCheng on 2017/12/28.
 */
object RouterPath {
    class UserCenter {
        companion object {
            const val PATH_LOGIN = "/userCenter/login"
            const val PATH_ORDER = "/userCenter/order"
        }
    }

    class PaySDK {
        companion object {
            const val PATH_PAY = "/paySDK/pay"
        }
    }
}