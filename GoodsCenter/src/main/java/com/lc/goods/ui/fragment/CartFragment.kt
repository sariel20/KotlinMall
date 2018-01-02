package com.lc.goods.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.YuanFenConverter
import com.lc.base.ext.onClick
import com.lc.base.ext.setVis
import com.lc.base.ext.startLoading
import com.lc.base.ui.fragment.BaseMvpFragment
import com.lc.base.utils.AppPrefsUtils
import com.lc.goods.R
import com.lc.goods.common.GoodsConstant
import com.lc.goods.data.protocol.CartGoods
import com.lc.goods.event.CartAllCheckedEvent
import com.lc.goods.event.UpdateCartSizeEvent
import com.lc.goods.event.UpdateTotalPriceEvent
import com.lc.goods.injection.component.DaggerCartComponent
import com.lc.goods.injection.module.CartModule
import com.lc.goods.presenter.CartListPresenter
import com.lc.goods.presenter.view.CartListView
import com.lc.goods.ui.adapter.CartGoodsAdapter
import com.lc.provider.common.ProviderConstant
import com.lc.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by LiangCheng on 2017/12/27.
 */
class CartFragment : BaseMvpFragment<CartListPresenter>(), CartListView, View.OnClickListener {

    private lateinit var mAdapter: CartGoodsAdapter

    private var mTotalPrice: Long = 0

    override fun injectComponent() {
        DaggerCartComponent.builder().activityComponent(activityComponent)
                .cartModule(CartModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = CartGoodsAdapter(context)
        mCartGoodsRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<CartGoods> {
            override fun onItemClick(item: CartGoods, position: Int) {
            }

        })

        mAllCheckedCb.onClick(this)

        mDeleteBtn.onClick(this)

        mSettleAccountsBtn.onClick(this)

        mHeaderBar.getRightView().onClick {
            refreshEditStatus()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
        /*全选*/
            R.id.mAllCheckedCb -> {
                for (item in mAdapter.dataList) {
                    item.isSelected = mAllCheckedCb.isChecked
                }
                mAdapter.notifyDataSetChanged()
                updateTotalPrice()
            }
        /*删除*/
            R.id.mDeleteBtn -> {
                val cartIdList: MutableList<Int> = arrayListOf()
                mAdapter.dataList.filter {
                    it.isSelected
                }.mapTo(cartIdList) { it.id }
                if (cartIdList.size == 0) {
                    toast("请选择需要删除的商品")
                } else {
                    mPresenter.deleteCartList(cartIdList)
                }
            }
        /*提交*/
            R.id.mSettleAccountsBtn -> {
                val cartGoodsList: MutableList<CartGoods> = arrayListOf()
                mAdapter.dataList.filter {
                    it.isSelected
                }.mapTo(cartGoodsList) {
                    it
                }
                if (cartGoodsList.size == 0) {
                    toast("请选择商品")
                } else {
                    mPresenter.submitCart(cartGoodsList, mTotalPrice)
                }
            }
        }
    }

    /*编辑状态*/
    private fun refreshEditStatus() {
        /*判断标题右侧文字是否是“完成”*/
        val isEditStatus = getString(R.string.common_edit) ==
                mHeaderBar.getRightText()

        mHeaderBar.getRightView().text =
                if (isEditStatus) {
                    "完成"
                } else {
                    "编辑"
                }

        mTotalPriceTv.setVis(isEditStatus.not())
        mSettleAccountsBtn.setVis(isEditStatus.not())
        mDeleteBtn.setVis(isEditStatus)
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mHeaderBar.getRightView().setVis(true)
            mAllCheckedCb.isChecked = false
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT//加载完成后改变视图状态
        } else {
            mHeaderBar.getRightView().setVis(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        /*更新底部购物车商品数量*/
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, result?.size ?: 0)
        Bus.send(UpdateCartSizeEvent())
        updateTotalPrice()
    }

    private fun initObserver() {
        /*CheckBox监听*/
        Bus.observe<CartAllCheckedEvent>()
                .subscribe { t: CartAllCheckedEvent ->
                    run {
                        /*根据item单选框 判断全选按钮是否选中*/
                        mAllCheckedCb.isChecked = t.isAllChecked
                        updateTotalPrice()
                    }
                }.registerInBus(this)

        /*价格监听*/
        Bus.observe<UpdateTotalPriceEvent>()
                .subscribe {
                    updateTotalPrice()
                }.registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun updateTotalPrice() {
        mTotalPrice = mAdapter.dataList
                /*遍历选中item*/
                .filter { it.isSelected }
                /*计算总价*/
                .map { it.goodsCount * it.goodsPrice }
                .sum()
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    /*删除商品回调*/
    override fun onDeleteCartListResult(result: Boolean?) {
        toast("删除成功")
        refreshEditStatus()
        loadData()
    }

    /*提交购物车回调*/
    override fun onSubmipCartResult(result: Int) {
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_ORDER).withInt(ProviderConstant.KEY_ORDER_ID, result).navigation()
    }

    /*暴露左侧回退按钮方法，供cartActivity使用*/
    fun setBackVisible(vis: Boolean) {
        mHeaderBar.getLeftView().visibility = if (vis) View.VISIBLE else View.GONE
    }
}