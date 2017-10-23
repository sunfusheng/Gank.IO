package com.sunfusheng.gank.ui

import android.os.Bundle
import android.text.TextUtils
import com.sunfusheng.gank.R
import com.sunfusheng.gank.base.BaseActivity
import com.sunfusheng.gank.http.Api
import com.sunfusheng.gank.ui.gank.GankFragment
import com.sunfusheng.gank.util.ToastUtil
import com.sunfusheng.gank.util.Util
import com.sunfusheng.gank.util.update.UpdateHelper
import com.sunfusheng.gank.util.update.VersionEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class KotlinMainActivity : BaseActivity() {

    private val END_TIME_SECONDS: Long = 2
    private var updateHelper: UpdateHelper = UpdateHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, GankFragment())
                .commitAllowingStateLoss()

        checkVersion()
        prepareForExiting()
    }

    private fun checkVersion() {
        Api.getInstance().apiService.checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose<VersionEntity>(bindToLifecycle<VersionEntity>())
                .filter { TextUtils.isEmpty(it.version) }
                .filter { Integer.parseInt(it.version) > Util.getVersionCode(this) }
                .subscribe({ updateHelper.dealWithVersion(it) }, { it.printStackTrace() })
    }

    private fun prepareForExiting() {
        lifecycle.throttleFirst(END_TIME_SECONDS, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe({ ToastUtil.show(this, getString(R.string.exit_tip)) }, { it.printStackTrace() })

        lifecycle.compose(bindToLifecycle<Any>())
                .timeInterval(AndroidSchedulers.mainThread())
                .skip(1)
                .filter { it.time(TimeUnit.SECONDS) < END_TIME_SECONDS }
                .subscribe({ finish() }, { it.printStackTrace() })
    }

    override fun onDestroy() {
        updateHelper.unInit()
        super.onDestroy()
    }

    override fun onBackPressed() {
        lifecycle?.onNext(0)
    }
}

