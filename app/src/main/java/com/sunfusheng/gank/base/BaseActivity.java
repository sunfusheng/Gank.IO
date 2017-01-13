package com.sunfusheng.gank.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
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
        StatusBarUtil.setStatusBarTranslucent(this, true);
        inflateView();
    }

    private void inflateView() {
        onBeforeInflation();
        setContentView();
        getWindow().setBackgroundDrawable(null);
        onAfterInflation();
    }

    protected void onBeforeInflation() {}

    protected abstract void setContentView();

    protected void onAfterInflation() {}

}
