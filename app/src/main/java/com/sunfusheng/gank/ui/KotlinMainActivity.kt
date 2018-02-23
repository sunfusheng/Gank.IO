package com.sunfusheng.gank.ui

import android.os.Bundle
import com.sunfusheng.FirUpdater
import com.sunfusheng.gank.Constants
import com.sunfusheng.gank.R
import com.sunfusheng.gank.base.BaseActivity
import com.sunfusheng.gank.ui.gank.GankFragment
import com.sunfusheng.gank.util.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class KotlinMainActivity : BaseActivity() {

    private val END_TIME_SECONDS: Long = 2

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
        FirUpdater(this, Constants.FIR_IM_API_TOKEN, Constants.FIR_IM_APP_ID).checkVersion()
    }

    private fun prepareForExiting() {
        lifecycle.throttleFirst(END_TIME_SECONDS, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe({ ToastUtil.toast(R.string.exit_tip) }, { it.printStackTrace() })

        lifecycle.compose(bindToLifecycle<Any>())
                .timeInterval(AndroidSchedulers.mainThread())
                .skip(1)
                .filter { it.time(TimeUnit.SECONDS) < END_TIME_SECONDS }
                .subscribe({ finish() }, { it.printStackTrace() })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        lifecycle?.onNext(0)
    }
}

