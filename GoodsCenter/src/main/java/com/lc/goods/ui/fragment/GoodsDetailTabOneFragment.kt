package com.lc.goods.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.utils.YuanFenConverter
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseActivity
import com.lc.base.ui.fragment.BaseMvpFragment
import com.lc.base.widgets.BannerImageLoader
import com.lc.goods.R
import com.lc.goods.common.GoodsConstant
import com.lc.goods.data.protocol.Goods
import com.lc.goods.event.AddCartEvent
import com.lc.goods.event.GoodsDetailImageEvent
import com.lc.goods.event.SkuChangedEvent
import com.lc.goods.event.UpdateCartSizeEvent
import com.lc.goods.injection.component.DaggerGoodsComponent
import com.lc.goods.injection.module.GoodsModule
import com.lc.goods.presenter.GoodsDetailPresenter
import com.lc.goods.presenter.view.GoodsDetailView
import com.lc.goods.widget.GoodsSkuPopView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*

/**
 * Created by LiangCheng on 2017/12/28.
 */
class GoodsDetailTabOneFragment : BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {
    private lateinit var mSkuPop: GoodsSkuPopView
    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation

    private var cartGoods: Goods? = null

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(activityComponent)
                .goodsModule(GoodsModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_goods_detail_tab_one, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnim()
        initPop()
        loadData()
        initObserver()
    }

    /*初始化popwindow*/
    private fun initPop() {
        mSkuPop = GoodsSkuPopView(activity)
        mSkuPop.setOnDismissListener {
            (activity as BaseActivity).contentView.startAnimation(mAnimationEnd)
        }
    }

    /*初始化视图*/
    private fun initView() {
        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Accordion)
        mGoodsDetailBanner.setDelayTime(2000)
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)

        mSkuView.onClick {
            /*显示弹框*/
            mSkuPop.showAsDropDown((activity as BaseActivity).contentView
                    , Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL, 0, 0)
            /*启动缩放动画*/
            (activity as BaseActivity).contentView.startAnimation(mAnimationStart)
        }
    }

    /*初始化缩放动画*/
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
                1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
                0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }

    /*获取商品详情数据*/
    private fun loadData() {
        mPresenter.getGoodsDetail(activity.intent.getIntExtra(GoodsConstant.KEY_GOODS_ID, -1))
    }

    /*显示详情数据*/
    override fun onGetGoodsDetailResult(result: Goods) {
        cartGoods = result

        mGoodsDetailBanner.setImages(result.goodsBanner.split(","))
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.goodsDesc
        mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        //使用rxbus将详情图片传递到tab2
        Bus.send(GoodsDetailImageEvent(result.goodsDetailOne, result.goodsDetailTwo))

        loadPopData(result)
    }

    /*加载弹框数据*/
    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)

        mSkuPop.setSkuData(result.goodsSku)
    }

    private fun initObserver() {
        /*监听弹框sku、数量变更*/
        Bus.observe<SkuChangedEvent>()
                .subscribe {
                    mSkuSelectedTv.text = mSkuPop.getSelectSku() +
                            GoodsConstant.SKU_SEPARATOR +
                            mSkuPop.getSelectCount() +
                            "件"
                }.registerInBus(this)

        Bus.observe<AddCartEvent>()
                .subscribe {
                    addCart()
                }.registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /*添加购物车*/
    private fun addCart() {
        cartGoods?.let {
            mPresenter.addCart(it.id,
                    it.goodsDesc,
                    it.goodsDefaultIcon,
                    it.goodsDefaultPrice,
                    mSkuPop.getSelectCount(),
                    mSkuPop.getSelectSku())
        }
    }

    /*添加购物车回调*/
    override fun onAddCartResult(result: Int) {
        Bus.send(UpdateCartSizeEvent())
    }
}