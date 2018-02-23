package com.sunfusheng.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunfusheng.FirUpdater;
import com.sunfusheng.gank.Constants;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.ui.gank.GankFragment;
import com.sunfusheng.gank.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    private static final long END_TIME_SECONDS = 2;

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
        new FirUpdater(this, Constants.FIR_IM_API_TOKEN, Constants.FIR_IM_APP_ID).checkVersion();
    }

    private void prepareForExiting() {
        lifecycle.throttleFirst(END_TIME_SECONDS, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(it -> ToastUtil.toast(R.string.exit_tip), Throwable::printStackTrace);

        lifecycle.compose(bindToLifecycle())
                .timeInterval(AndroidSchedulers.mainThread())
                .skip(1)
                .filter(it -> it.time(TimeUnit.SECONDS) < END_TIME_SECONDS)
                .subscribe(it -> finish(), Throwable::printStackTrace);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (lifecycle != null) {
            lifecycle.onNext(0);
        }
    }
}
