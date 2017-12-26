package com.lc.user.ui.activity

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
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.GlideUtils
import com.lc.base.common.BaseConstant
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseMvpActivity
import com.lc.user.R
import com.lc.user.injection.component.DaggerUserComponent
import com.lc.user.injection.module.UserModule
import com.lc.user.presenter.UserInfoPresenter
import com.lc.user.presenter.view.UserInfoView
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.activity_user_info.*
import java.io.File


/**
 * 用户信息
 */
class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, TakePhoto.TakeResultListener {

    //拍照后文件
    private lateinit var mTakeFile: File

    //拍照
    private val MTAKEPHOTO_CAMERA = 1
    //本地相册
    private val MTAKEPHOTO_LOCATION = 2
    //相机权限
    private val OPEN_CANMER = 3

    //七牛初始化
    private val mUploadManager: UploadManager by lazy {
        UploadManager()
    }

    //图片选择
    private lateinit var mTakePhoto: TakePhoto

    //本地图片地址
    private var mLocalFileUrl: String? = null

    //服务器获取图片地址
    private lateinit var mRemoteFile: String

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        mUserIconView.onClick {
            showAlertView()
        }
    }

    private fun showAlertView() {
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
     * 获取图片成功
     */
    override fun takeSuccess(result: TResult?) {
        mLocalFileUrl = result?.image?.originalPath
        //上传图片凭证
        mPresenter.getUploadToken()
    }

    override fun takeCancel() {}
    /**
     * 获取图片失败
     */
    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takeFail", msg)
    }

    /**
     * 服务器返回图片地址
     */
    override fun onGetUploadTokenResult(result: String) {
        mUploadManager.put(mLocalFileUrl, null, result, { _, _, response ->
            mRemoteFile = BaseConstant.IMAGE_SERVER_ADDRESS + response?.get("hash")
            GlideUtils.loadImage(this@UserInfoActivity, mRemoteFile, mUserIconIv)
        }, null)
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