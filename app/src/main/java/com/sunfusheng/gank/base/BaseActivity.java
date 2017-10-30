package com.sunfusheng.gank.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.sunfusheng.gank.ui.MainActivity;
import com.sunfusheng.gank.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author by sunfusheng on 2016/12/24.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    protected Activity mActivity;
    protected Context mContext;
    protected Subject<Object> lifecycle = PublishSubject.create().toSerialized();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;
        Logger.d("log-activity: " + getClass().getSimpleName() + ".java");
        setStatusBarTranslucent(false);
    }

    protected void setStatusBarTranslucent(boolean isLightStatusBar) {
        StatusBarUtil.setStatusBarTranslucent(this, isLightStatusBar);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent == null) return;
        if (intent.getComponent() == null) return;
        String className = intent.getComponent().getClassName();
        if (!className.equals(MainActivity.class.getName())) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (intent == null) return;
        if (intent.getComponent() == null) return;
        String className = intent.getComponent().getClassName();
        if (!className.equals(MainActivity.class.getName())) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (!((Object) this).getClass().equals(MainActivity.class)) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lifecycle != null) {
            lifecycle.onComplete();
            lifecycle = null;
        }
    }
}
