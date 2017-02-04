package com.sunfusheng.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.http.Api;
import com.sunfusheng.gank.ui.gank.GankFragment;
import com.sunfusheng.gank.util.Utils;
import com.sunfusheng.gank.util.update.UpdateApp;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

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
                .takeUntil(lifecycle)
                .filter(entity -> entity != null)
                .filter(entity -> Integer.parseInt(entity.getVersion()) > Utils.getVersionCode())
                .subscribe(entity -> {
                    new UpdateApp(this).dealWithVersion(entity);
                }, Throwable::printStackTrace);
    }

}
