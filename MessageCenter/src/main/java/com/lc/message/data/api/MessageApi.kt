package com.lc.message.data.api

import com.lc.base.data.protocol.BaseResp
import com.lc.message.data.protocol.Message
import retrofit2.http.POST
import rx.Observable

/*
    消息 接口
 */
interface MessageApi {

    /*
        获取消息列表
     */
    @POST("msg/getList")
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>

}
