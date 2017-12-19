package com.lc.base.rx

import rx.Subscriber

/**
 * Created by LiangCheng on 2017/12/19.
 */
open class BaseSubscriber<T> : Subscriber<T>() {
    override fun onError(e: Throwable?) {
    }

    override fun onNext(t: T) {
    }

    override fun onCompleted() {
    }
}