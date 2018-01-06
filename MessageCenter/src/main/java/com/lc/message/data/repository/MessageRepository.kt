package com.lc.message.data.repository


import com.lc.base.data.net.RetrofitFactory
import com.lc.base.data.protocol.BaseResp
import com.lc.message.data.api.MessageApi
import com.lc.message.data.protocol.Message
import rx.Observable
import javax.inject.Inject


/*
   消息数据层
 */
class MessageRepository @Inject constructor() {

    /*
        获取消息列表
     */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>> {
        return RetrofitFactory.instance.create(MessageApi::class.java).getMessageList()
    }



}
