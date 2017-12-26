package com.lc.user.service

import rx.Observable

/**
 * Created by LiangCheng on 2017/12/19.
 */
interface UploadService {
    fun getUploadToken(): Observable<String>

}