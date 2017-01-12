package com.sunfusheng.gank.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunfusheng.gank.util.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by sunfusheng on 2016/12/24.
 */

public class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, true);
    }
}
