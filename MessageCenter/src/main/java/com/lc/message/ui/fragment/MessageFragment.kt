package com.lc.message.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.lc.base.ext.startLoading
import com.lc.base.ui.fragment.BaseMvpFragment
import com.lc.message.R
import com.lc.message.data.protocol.Message
import com.lc.message.injection.component.DaggerMessageComponent
import com.lc.message.injection.module.MessageModule
import com.lc.message.presenter.MessagePresenter
import com.lc.message.presenter.view.MessageView
import com.lc.message.ui.adapter.MessageAdapter
import com.lc.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * Created by LiangCheng on 2018/1/6.
 */
class MessageFragment : BaseMvpFragment<MessagePresenter>(), MessageView {
    override fun injectComponent() {
        DaggerMessageComponent.builder().activityComponent(activityComponent)
                .messageModule(MessageModule()).build().inject(this)
        mPresenter.mView = this
    }

    private lateinit var mAdapter: MessageAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getMessageList()
    }

    private fun initView() {
        mMessageRv.layoutManager = LinearLayoutManager(context)
        mAdapter = MessageAdapter(context)
        mMessageRv.adapter = mAdapter
    }

    override fun onGetMessageListResult(result: MutableList<Message>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /*点击模块红点消失*/
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            Bus.send(MessageBadgeEvent(false))
        }
    }
}