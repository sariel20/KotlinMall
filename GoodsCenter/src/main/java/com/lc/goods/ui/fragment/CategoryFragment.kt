package com.lc.goods.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lc.base.ext.setVis
import com.lc.base.ext.startLoading
import com.lc.base.ui.fragment.BaseMvpFragment
import com.lc.goods.R
import com.lc.goods.common.GoodsConstant
import com.lc.goods.data.protocol.Category
import com.lc.goods.injection.component.DaggerCategoryComponent
import com.lc.goods.injection.module.CategoryModule
import com.lc.goods.presenter.CategoryPresenter
import com.lc.goods.presenter.view.CategoryView
import com.lc.goods.ui.activity.GoodsActivity
import com.lc.goods.ui.adapter.SecondCategoryAdapter
import com.lc.goods.ui.adapter.TopCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by LiangCheng on 2017/12/27.
 */
class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), CategoryView {

    lateinit var topAdapter: TopCategoryAdapter
    lateinit var secondAdapter: SecondCategoryAdapter

    override fun injectComponent() {
        DaggerCategoryComponent.builder().activityComponent(activityComponent)
                .categoryModule(CategoryModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }


    private fun initView() {
        mTopCategoryRv.layoutManager = LinearLayoutManager(context)
        topAdapter = TopCategoryAdapter(context)
        mTopCategoryRv.adapter = topAdapter

        topAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                for (category in topAdapter.dataList) {
                    category.isSelected = item.id == category.id
                }
                topAdapter.notifyDataSetChanged()
                loadData(item.id)
            }
        })

        mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
        secondAdapter = SecondCategoryAdapter(context)
        mSecondCategoryRv.adapter = secondAdapter
        secondAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                startActivity<GoodsActivity>(GoodsConstant.KEY_CATEGORY_ID to item.id)
            }
        })
    }

    private fun loadData(parentId: Int = 0) {
        if (parentId != 0) {
            //加载数据时显示loading动画
            mMultiStateView.startLoading()
        }
        mPresenter.getCategory(parentId)

    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if (result != null && result.size > 0) {
            result?.let {
                if (result[0].parentId == 0) {//一级分类
                    result[0].isSelected = true
                    topAdapter.setData(result)
                    mPresenter.getCategory(result[0].id)
                } else {//二级分类
                    secondAdapter.setData(result)
                    mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT//加载完成后改变视图状态
                }
                mTopCategoryIv.setVis(true)
                mCategoryTitleTv.setVis(true)
            }
        } else {
            //无数据状态
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
            mTopCategoryIv.setVis(false)
            mCategoryTitleTv.setVis(false)
        }

    }

}