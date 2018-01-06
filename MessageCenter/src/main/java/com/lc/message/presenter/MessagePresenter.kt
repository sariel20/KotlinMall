package com.lc.message.presenter

import com.lc.base.ext.execute
import com.lc.base.presenter.BasePresenter
import com.lc.base.rx.BaseSubscriber
import com.lc.message.data.protocol.Message
import com.lc.message.presenter.view.MessageView
import com.lc.message.service.MessageService
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
class MessagePresenter @Inject constructor() : BasePresenter<MessageView>() {
    @Inject
    lateinit var messageService: MessageService

    fun getMessageList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        messageService.getMessageList()
                .execute(object : BaseSubscriber<MutableList<Message>?>(mView) {
                    override fun onNext(t: MutableList<Message>?) {
                        mView.onGetMessageListResult(t)
                    }
                }, lifecycleProvider)

    }


}