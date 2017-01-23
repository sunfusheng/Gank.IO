package com.sunfusheng.gank.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.sunfusheng.gank.ui.MainActivity;
import com.sunfusheng.gank.util.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by sunfusheng on 2016/12/24.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("log-activity", getClass().getSimpleName() + ".java");
        setStatusBarTranslucent(false);
    }

    protected void setStatusBarTranslucent(boolean isLightStatusBar) {
        StatusBarUtil.setStatusBarTranslucent(this, false);
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

}
