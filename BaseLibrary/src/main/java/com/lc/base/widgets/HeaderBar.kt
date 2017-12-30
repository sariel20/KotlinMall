package com.lc.base.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.lc.base.R
import com.lc.base.ext.onClick
import kotlinx.android.synthetic.main.layout_header_bar.view.*

/**
 * Created by LiangCheng on 2017/12/22.
 */
class HeaderBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isShowBack = true
    private var titleText: String? = null
    private var rightText: String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)

        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true)
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)

        initView()
        typedArray.recycle()
    }

    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)

        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.INVISIBLE

        titleText?.let {
            mTitleTv.text = titleText
        }

        rightText?.let {
            mRightTv.text = rightText
            mRightTv.visibility = View.VISIBLE
        }

        mLeftIv.onClick {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }
    }
    fun getLeftView(): ImageView {
        return mLeftIv
    }
    fun getRightView(): TextView {
        return mRightTv
    }

    fun getRightText(): String {
        return mRightTv.text.toString()
    }
}