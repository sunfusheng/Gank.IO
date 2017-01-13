package com.sunfusheng.gank.base;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);
    void onAttached();
    void onDetached();
}
