package com.sunfusheng.gank.ui.gank;

import com.sunfusheng.gank.base.BasePresenter;
import com.sunfusheng.gank.base.BaseView;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public interface GankContract {

    interface Presenter extends BasePresenter {

        void loadGankList();
    }

    interface View extends BaseView<Presenter> {

        void showGankList();
    }
}
