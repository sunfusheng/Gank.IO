package com.sunfusheng.gank.ui.main;

import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.ui.gank.GankFragment;
import com.sunfusheng.gank.widget.CircleLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.circleLoadingView)
    CircleLoadingView circleLoadingView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new GankFragment())
                .commit();
    }

    private void initData() {

    }

    private void initView() {

    }

    private void initListener() {

    }


}
