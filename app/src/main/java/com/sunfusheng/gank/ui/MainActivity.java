package com.sunfusheng.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.http.Api;
import com.sunfusheng.gank.ui.gank.GankFragment;
import com.sunfusheng.gank.util.AppUtil;
import com.sunfusheng.gank.util.ToastUtil;
import com.sunfusheng.gank.util.update.UpdateHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final long END_TIME_SECONDS = 2;
    private UpdateHelper updateHelper = new UpdateHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new GankFragment())
                .commitAllowingStateLoss();

        checkVersion();
        prepareForExiting();
    }

    private void checkVersion() {
        Api.getInstance().getApiService().checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .filter(it -> it != null)
                .filter(it -> !TextUtils.isEmpty(it.version))
                .filter(it -> Integer.parseInt(it.version) > AppUtil.getVersionCode())
                .subscribe(it -> updateHelper.dealWithVersion(it), Throwable::printStackTrace);
    }

    private void prepareForExiting() {
        lifecycle.throttleFirst(END_TIME_SECONDS, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(it -> ToastUtil.show(this, getString(R.string.exit_tip)), Throwable::printStackTrace);

        lifecycle.compose(bindToLifecycle())
                .timeInterval(AndroidSchedulers.mainThread())
                .skip(1)
                .filter(it -> it.time(TimeUnit.SECONDS) < END_TIME_SECONDS)
                .subscribe(it -> finish(), Throwable::printStackTrace);
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
            lifecycle.onNext(0);
        }
    }
}
