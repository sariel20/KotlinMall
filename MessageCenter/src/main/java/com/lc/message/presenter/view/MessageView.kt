package com.lc.message.presenter.view

import com.lc.base.presenter.view.BaseView
import com.lc.message.data.protocol.Message

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface MessageView : BaseView {

    fun onGetMessageListResult(result: MutableList<Message>?)
}