package com.sunfusheng.gank.ui.gank;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by sunfusheng on 2017/1/13.
 */

public class GankView extends FrameLayout implements GankContract.View {

    public GankView(@NonNull Context context) {
        this(context, null);
    }

    public GankView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GankView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {

    }

    @Override
    public void onAttached() {

    }

    @Override
    public void onDetached() {

    }
}
