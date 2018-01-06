package com.lc.message.service.impl


import com.lc.base.ext.convert
import com.lc.message.data.protocol.Message
import com.lc.message.data.repository.MessageRepository
import com.lc.message.service.MessageService
import javax.inject.Inject

import rx.Observable

/*
   消息业务层
 */
class MessageServiceImpl @Inject constructor() : MessageService {

    @Inject
    lateinit var repository: MessageRepository

    /*
        获取消息列表
     */
    override fun getMessageList(): Observable<MutableList<Message>?> {
        return repository.getMessageList().convert()
    }

}
