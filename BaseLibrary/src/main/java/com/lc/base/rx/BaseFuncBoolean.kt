package com.lc.base.rx

import com.lc.base.common.ResultCode
import com.lc.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

/**
 * Created by LiangCheng on 2017/12/21.
 */
class BaseFuncBoolean<T> : Func1<BaseResp<T>, Observable<Boolean>> {
    override fun call(t: BaseResp<T>): Observable<Boolean> {
        if (t.status != ResultCode.SUCCESS) {
            return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(true)
    }
}