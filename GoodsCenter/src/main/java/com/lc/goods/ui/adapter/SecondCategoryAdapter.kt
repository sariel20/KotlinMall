package com.lc.goods.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.GlideUtils
import com.lc.base.ext.loadUrl
import com.lc.goods.R
import com.lc.goods.data.protocol.Category
import kotlinx.android.synthetic.main.layout_second_category_item.view.*
import kotlinx.android.synthetic.main.layout_top_category_item.view.*

/**
 * Created by LiangCheng on 2017/12/27.
 */
class SecondCategoryAdapter(context: Context) : BaseRecyclerViewAdapter<Category, SecondCategoryAdapter.ViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_second_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mCategoryIconIv.loadUrl(model.categoryIcon)
        holder.itemView.mSecondCategoryNameTv.text = model.categoryName
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}