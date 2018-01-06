package com.lc.user.ui.activity

import android.os.Bundle
import android.util.Log
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.GlideUtils
import com.kotlin.user.utils.UserPrefsUtils
import com.lc.base.common.BaseConstant
import com.lc.base.ext.onClick
import com.lc.base.ui.activity.BaseTakePhotoActivity
import com.lc.base.utils.AppPrefsUtils
import com.lc.provider.common.ProviderConstant
import com.lc.user.R
import com.lc.user.data.protocol.UserInfo
import com.lc.user.injection.component.DaggerUserComponent
import com.lc.user.injection.module.UserModule
import com.lc.user.presenter.UserInfoPresenter
import com.lc.user.presenter.view.UserInfoView
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast


/**
 * 用户信息
 */
class UserInfoActivity : BaseTakePhotoActivity<UserInfoPresenter>(), UserInfoView {

    //七牛初始化
    private val mUploadManager: UploadManager by lazy {
        UploadManager()
    }
    //本地图片地址
    private var mLocalFileUrl: String? = null
    //服务器获取图片地址
    private lateinit var mRemoteFileUrl: String

    /**
     * 用户信息
     */
    private var mUserIcon: String? = null
    private var mUserName: String? = null
    private var mUserMobile: String? = null
    private var mUserGender: String? = null//0男1女
    private var mUserSign: String? = null

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)


        initView()
        initData()
    }

    /**
     * 获取本地存储用户数据并显示
     */
    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

        if (mUserIcon != "") {
            mRemoteFileUrl = mUserIcon as String
            GlideUtils.loadUrlImage(this, mUserIcon!!, mUserIconIv)
        }
        mUserNameEt.setText(mUserName)
        if (mUserGender == "0")
            mGenderMaleRb.isChecked = true
        else
            mGenderFemaleRb.isChecked = true

        mUserSignEt.setText(mUserSign)

        mUserMobileTv.text = mUserMobile
    }

    private fun initView() {
        mUserIconView.onClick {
            showAlertView()
        }

        mHeaderBar.getRightView().onClick {
            mPresenter.editUser(mRemoteFileUrl!!,
                    mUserNameEt.text?.toString() ?: "",
                    if (mGenderMaleRb.isChecked) "0" else "1",
                    mUserSignEt.text?.toString() ?: "")
        }
    }


    /**
     * 服务器返回图片地址
     */
    override fun onGetUploadTokenResult(result: String) {
        mUploadManager.put(mLocalFileUrl, null, result, { _, _, response ->
            mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + response?.get("hash")
            Log.e("RemoteFile", mRemoteFileUrl)
            GlideUtils.loadImage(this@UserInfoActivity, mRemoteFileUrl, mUserIconIv)
        }, null)
    }

    /**
     * 修改资料回调
     */
    override fun onEditUserResult(result: UserInfo) {
        toast("修改成功")
        UserPrefsUtils.putUserInfo(result)
    }

    /**
     * 获取图片成功
     */
    override fun takeSuccess(result: TResult?) {
        mLocalFileUrl = result?.image?.originalPath
        //上传图片凭证
        mPresenter.getUploadToken()
    }


}