package com.sunfusheng.gank.base;

/**
 * @author by sunfusheng on 2017/1/13.
 */
public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
    void onAttach();
    void onDetach();
}
