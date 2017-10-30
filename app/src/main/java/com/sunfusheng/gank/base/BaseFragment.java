package com.sunfusheng.gank.base;

import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author by sunfusheng on 2017/1/12.
 */
public class BaseFragment extends RxFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("log-fragment: " + getClass().getSimpleName() + ".java");
    }

}
