package com.sunfusheng.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.util.AppUtil;
import com.sunfusheng.gank.widget.WebViewLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/9/8.
 * <p>
 * Logo 字母格式 Allura
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.webViewLayout)
    WebViewLayout webViewLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private String url = "file:///android_asset/about_rxgank.html";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initToolBar();
        webViewLayout.loadUrl(url);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle(getString(R.string.app_name) + AppUtil.getVersionName());
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewLayout.onPause();
    }

    @Override
    protected void onDestroy() {
        webViewLayout.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webViewLayout.goBack()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
