package com.sunfusheng.gank.ui.main;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.ui.gank.GankFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new GankFragment())
                .commitAllowingStateLoss();
    }

    private void initData() {

    }

    private void initView() {

    }

    private void initListener() {

    }

}
