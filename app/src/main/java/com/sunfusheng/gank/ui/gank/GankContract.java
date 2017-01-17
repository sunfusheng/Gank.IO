package com.sunfusheng.gank.ui.gank;

import com.sunfusheng.gank.base.BasePresenter;
import com.sunfusheng.gank.base.BaseView;

import java.util.List;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public interface GankContract {

    interface Presenter extends BasePresenter {

        void loadList();
        void loadMoreList();
    }

    interface View extends BaseView<Presenter> {

        void onLoading();
        void onSuccess(List<?> list);
        void onError();
        void onEmpty();
    }
}
