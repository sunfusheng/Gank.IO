package com.sunfusheng.gank.ui.gank;

import android.support.annotation.NonNull;

import com.sunfusheng.gank.base.BasePresenter;
import com.sunfusheng.gank.base.BaseView;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author by sunfusheng on 2017/1/13.
 */
public interface GankContract {

    interface Presenter extends BasePresenter {
        void onLoad();

        void onLoadMore();
    }

    interface View extends BaseView<Presenter> {
        <T> void register(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder);

        void onLoading();

        void onSuccess(List<Object> list, boolean isLoadMore);

        void onError();

        void onEmpty();
    }
}
