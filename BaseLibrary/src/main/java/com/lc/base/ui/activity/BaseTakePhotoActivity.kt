package com.lc.base.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.DateUtils
import com.lc.base.common.BaseApplication
import com.lc.base.injection.component.ActivityComponent
import com.lc.base.injection.component.DaggerActivityComponent
import com.lc.base.injection.module.ActivityModule
import com.lc.base.injection.module.LifecycleProviderModule
import com.lc.base.presenter.BasePresenter
import com.lc.base.presenter.view.BaseView
import com.lc.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import java.io.File
import javax.inject.Inject

/**
 * Created by LiangCheng on 2017/12/19.
 */
open abstract class BaseTakePhotoActivity<T : BasePresenter<*>> : BaseActivity(), BaseView, TakePhoto.TakeResultListener {

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

    @Inject
    lateinit var mPresenter: T
    lateinit var activityComponent: ActivityComponent
    lateinit var mLoadingDialog: ProgressLoading
    //相机权限
    private val OPEN_CANMER = 3
    //图片选择
    private lateinit var mTakePhoto: TakePhoto
    //拍照后文件
    private lateinit var mTakeFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)

        mLoadingDialog = ProgressLoading.create(this)
        ARouter.getInstance().inject(this)
    }

    //dagger2注册
    abstract fun injectComponent()


    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent(
                (application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    protected fun showAlertView() {
        AlertView("上传头像", null, "取消", null,
                arrayOf("拍照", "相册"), this,
                AlertView.Style.ActionSheet,
                OnItemClickListener { _, position ->
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                    when (position) {
                    //拍照
                        0 -> {
                            //创建临时文件
                            createTempFile()
                            //获取相机权限
                            if (Build.VERSION.SDK_INT >= 23) {
                                val checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), OPEN_CANMER)
                                    return@OnItemClickListener
                                } else {
                                    mTakePhoto.onPickFromCapture(Uri.fromFile(mTakeFile))
                                }
                            } else {
                                mTakePhoto.onPickFromCapture(Uri.fromFile(mTakeFile))
                            }

                        }
                    //相册
                        1 -> {
                            mTakePhoto.onPickFromGallery()
                        }
                    }
                }).show()
    }

    /**
     * 权限回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            OPEN_CANMER -> if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                mTakePhoto.onPickFromCapture(Uri.fromFile(mTakeFile))
            } else {
                Toast.makeText(this, "相机权限禁用了。请务必开启相机权", Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * 获取图片成功
     */
    override fun takeSuccess(result: TResult?) {
    }

    override fun takeCancel() {}
    /**
     * 获取图片失败
     */
    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takeFail", msg)
    }

    /**
     * 创建临时文件
     */
    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            this.mTakeFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }

        this.mTakeFile = File(filesDir, tempFileName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }
}