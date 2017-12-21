package com.lc.base.rx

import com.lc.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

/**
 * Created by LiangCheng on 2017/12/21.
 */
class BaseFunc<T> : Func1<BaseResp<T>, Observable<T>> {
    override fun call(t: BaseResp<T>): Observable<T> {
        if (t.stats != 0) {
            return Observable.error(BaseException(t.stats, t.message))
        }
        return Observable.just(t.data)
    }
}