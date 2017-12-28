package com.lc.base.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.lc.base.common.AppManager
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import org.jetbrains.anko.find

/**
 * Created by LiangCheng on 2017/12/19.
 */
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    val contentView: View
        get() {
            /*获取容器id*/
            val content = find<FrameLayout>(android.R.id.content)
            /*取容器的第一个元素，根布局*/
            return content.getChildAt(0)
        }
}