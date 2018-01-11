package com.lc.provider.router

/**
 * Created by LiangCheng on 2017/12/28.
 */
object RouterPath {
    class UserCenter {
        companion object {
            /*登录*/
            const val PATH_LOGIN = "/userCenter/login"
            /*购物车提交跳转订单确认*/
            const val PATH_ORDER = "/userCenter/order"
            /*购买成功后点击推送跳转到订单详情*/
            const val PATH_ORDER_DETAIL = "/userCenter/detail"
        }
    }

    class PaySDK {
        companion object {
            /*支付*/
            const val PATH_PAY = "/paySDK/pay"
        }
    }

    class MessageCenter {
        companion object {
            /*获取用户PUSHID*/
            const val PATH_MESSAGE_PUSH = "/messageCenter/push"
        }
    }
}