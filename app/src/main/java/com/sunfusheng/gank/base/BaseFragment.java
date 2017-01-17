package com.sunfusheng.gank.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

/**
 * Created by sunfusheng on 2017/1/12.
 */
public class BaseFragment extends Fragment {

    private boolean isCreated = false, isVisible = false, isEnter = false, isLoaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("log-fragment", getClass().getSimpleName() + ".java");
        isCreated = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public boolean isVisibleNow() {
        return isCreated && isVisible;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isVisible) {
            onLeave();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible) {
            onEnter();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isCreated && isVisible) {
            onEnter();
            if (!isLoaded)
                onLoad();
        } else {
            onLeave();
        }
    }

    protected void onEnter() {
        if (!isEnter && getContext() != null) {
            isEnter = true;
        }
    }

    private void onLeave() {
        if (isEnter && getContext() != null) {
            isEnter = false;
        }
    }

    public boolean isLazyLoaded() {
        return isLoaded;
    }

    private void onLoad() {
        isLoaded = true;
        new Handler(Looper.getMainLooper()).post(this::lazyLoad);
    }

    protected void lazyLoad() {
    }

    public boolean onBackPressed() {
        return false;
    }
}
