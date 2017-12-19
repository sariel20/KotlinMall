package com.lc.base.data.protocol

/**
 * Created by LiangCheng on 2017/12/19.
 */
class BaseResp<out T>(val stats: Int, val message: String, val data: T) {

}