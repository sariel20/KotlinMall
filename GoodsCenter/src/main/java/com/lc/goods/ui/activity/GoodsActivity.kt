package com.lc.goods.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.NetWorkUtils
import com.lc.base.ext.startLoading
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.goods.R
import com.lc.goods.common.GoodsConstant
import com.lc.goods.data.protocol.Goods
import com.lc.goods.injection.component.DaggerGoodsComponent
import com.lc.goods.injection.module.GoodsModule
import com.lc.goods.presenter.GoodsListPresenter
import com.lc.goods.presenter.view.GoodsListView
import com.lc.goods.ui.adapter.GoodsAdapter
import kotlinx.android.synthetic.main.activity_goods.*
import org.jetbrains.anko.startActivity

/**
 * Created by LiangCheng on 2017/12/28.
 * 商品列表
 */
class GoodsActivity : BaseMvpActivity<GoodsListPresenter>(), GoodsListView, BGARefreshLayout.BGARefreshLayoutDelegate {
    private lateinit var goodsAdapter: GoodsAdapter

    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1

    private var mData: MutableList<Goods>? = null

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(activityComponent)
                .goodsModule(GoodsModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)

        initView()
        initRefreshLayout()
        loadData()
    }

    /*初始化刷新*/
    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val refreshViewHolder = BGANormalRefreshViewHolder(this, true)
        /*刷新、加载背景颜色*/
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder)
    }

    private fun initView() {
        mGoodsRv.layoutManager = GridLayoutManager(this, 2)
        goodsAdapter = GoodsAdapter(this)
        mGoodsRv.adapter = goodsAdapter

        goodsAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Goods> {
            override fun onItemClick(item: Goods, position: Int) {
                startActivity<GoodsDetailActivity>(GoodsConstant.KEY_GOODS_ID to item.id)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        if (intent.getIntExtra(GoodsConstant.KEY_SEARCH_GOODS_TYPE, 0) != 0) {
            //搜索
            mPresenter.getGoodsListByKeyword(intent.getStringExtra(GoodsConstant.KEY_GOODS_KEYWORD), mCurrentPage)
        } else {
            //分类
            mPresenter.getGoodsList(intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID, 1), mCurrentPage)
        }
    }

    override fun onGetGoodsListResult(result: MutableList<Goods>?) {
        if (result != null && result.size > 0) {
            mMaxPage = result[0].maxPage
            if (mMaxPage == 1) {
                goodsAdapter.setData(result)
            } else {
                goodsAdapter.dataList.addAll(result)
                goodsAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT

        } else {
            //无数据状态
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        /*
        * 加载更多数据，或者更具产品需求实现上拉刷新也可以
        * 判断网络是否可用
        * */
        if (NetWorkUtils.isNetWorkAvailable(this)) {
            //判断是否最大页数
            return if (mCurrentPage < mMaxPage) {
                mCurrentPage++
                loadData()
                true
            } else {
                false
            }

        } else {
            //无网络结束刷新
            mRefreshLayout.endLoadingMore()
        }
        return false
    }

    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        // 加载最新数据
        if (NetWorkUtils.isNetWorkAvailable(this)) {
            //判断网络是否可用
            mCurrentPage = 1
            loadData()
        } else {
            //无网络结束加载
            mRefreshLayout.endRefreshing()
        }
    }
}