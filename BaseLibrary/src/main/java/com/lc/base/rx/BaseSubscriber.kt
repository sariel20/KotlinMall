package com.lc.base.rx

import com.lc.base.presenter.view.BaseView
import rx.Subscriber

/**
 * Created by LiangCheng on 2017/12/19.
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Subscriber<T>() {
    override fun onError(e: Throwable?) {
        baseView.hideLoading()
        if (e is BaseException) {
            baseView.onError(e.msg)
        }
    }

    override fun onNext(t: T) {
    }

    override fun onCompleted() {
        baseView.hideLoading()
    }
}