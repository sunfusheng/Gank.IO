package com.sunfusheng.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.http.Api;
import com.sunfusheng.gank.ui.gank.GankFragment;
import com.sunfusheng.gank.util.ToastUtil;
import com.sunfusheng.gank.util.AppUtil;
import com.sunfusheng.gank.util.update.UpdateHelper;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final int TIME_EXIT = 2000; // ms
    private UpdateHelper updateHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new GankFragment())
                .commitAllowingStateLoss();

        Api.getInstance().getApiService().checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .filter(it -> it != null)
                .filter(entity -> !TextUtils.isEmpty(entity.version))
                .filter(entity -> Integer.parseInt(entity.version) > AppUtil.getVersionCode())
                .subscribe(entity -> {
                    updateHelper = new UpdateHelper(this);
                    updateHelper.dealWithVersion(entity);
                }, Throwable::printStackTrace);

        lifecycle.asObservable()
                .throttleFirst(TIME_EXIT, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(it -> ToastUtil.show(mContext, "再按一次 退出应用"), Throwable::printStackTrace);

        lifecycle.asObservable()
                .compose(bindToLifecycle())
                .timeInterval(AndroidSchedulers.mainThread())
                .skip(1)
                .filter(it -> it.getIntervalInMilliseconds() < TIME_EXIT)
                .subscribe(it -> {
                    finish();
                }, Throwable::printStackTrace);
    }

    @Override
    protected void onDestroy() {
        if (updateHelper != null) {
            updateHelper.unInit();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (lifecycle != null) {
            lifecycle.onNext(null);
        }
    }
}
