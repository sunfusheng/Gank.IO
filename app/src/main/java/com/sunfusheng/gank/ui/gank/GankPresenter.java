package com.sunfusheng.gank.ui.gank;

import android.text.TextUtils;

import com.sunfusheng.gank.GankApp;
import com.sunfusheng.gank.http.Api;
import com.sunfusheng.gank.model.GankDay;
import com.sunfusheng.gank.model.GankDayResults;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.model.GankItemTitle;
import com.sunfusheng.gank.model.RequestParams;
import com.sunfusheng.gank.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankPresenter implements GankContract.Presenter {

    private GankView mView;
    private List<Object> mList;
    private RequestParams mRequestParams;
    protected Subject<Object> lifecycle = PublishSubject.create().toSerialized();

    public GankPresenter(GankView view) {
        this.mView = view;
    }

    @Override
    public void init() {
        mList = new ArrayList<>();
        mRequestParams = new RequestParams();
        if (mView != null) {
            mView.setPresenter(this);
            mView.onAttach();
        }
    }

    @Override
    public void unInit() {
        lifecycle.onNext(null);
        lifecycle.onComplete();
        if (mView != null) {
            mView.onDetach();
        }
    }

    @Override
    public void onRefresh() {
        if (AppUtil.isEmpty(mList)) {
            mView.onLoading();
        }
        mList = new ArrayList<>();
        mRequestParams = new RequestParams();
        getGankDayList(false);
    }

    @Override
    public void onLoadingMore() {
        getGankDayList(true);
    }

    private void getGankDayList(final boolean isLoadMore) {
        Api.getInstance().getApiService().getGankDay(mRequestParams.year, mRequestParams.month, mRequestParams.day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(gankDay -> gankDay != null)
                .filter(gankDay -> gankDay.results != null)
                .map(this::flatGankDay2List)
                .takeUntil(lifecycle)
                .subscribe(list -> {
                    if (AppUtil.notEmpty(list)) {
                        mList.addAll(list);
                        mRequestParams.onSuccess();
                    } else {
                        mRequestParams.onEmpty();
                    }
                    processRequestResult(isLoadMore);
                }, e -> {
                    e.printStackTrace();
                    mRequestParams.onError();
                    processRequestResult(isLoadMore);
                });
    }

    private void processRequestResult(boolean isLoadMore) {
        if (!mRequestParams.isComplete()) {
            getGankDayList(isLoadMore);
        } else {
            mRequestParams.onComplete();
            if (AppUtil.notEmpty(mList)) {
                processGirls(mList);
                mView.onSuccess(mList, isLoadMore);
            } else {
                mView.onError();
            }
        }
    }

    private List<Object> flatGankDay2List(GankDay gankDay) {
        List<Object> list = new ArrayList<>();
        GankDayResults results = gankDay.results;
        if (AppUtil.notEmpty(results.福利)) list.addAll(results.福利);
        if (AppUtil.notEmpty(results.Android)) {
            list.add(new GankItemTitle(results.Android.get(0)));
            list.addAll(results.Android);
        }
        if (AppUtil.notEmpty(results.iOS)) {
            list.add(new GankItemTitle(results.iOS.get(0)));
            list.addAll(results.iOS);
        }
        if (AppUtil.notEmpty(results.App)) {
            list.add(new GankItemTitle(results.App.get(0)));
            list.addAll(results.App);
        }
        if (AppUtil.notEmpty(results.瞎推荐)) {
            list.add(new GankItemTitle(results.瞎推荐.get(0)));
            list.addAll(results.瞎推荐);
        }
        if (AppUtil.notEmpty(results.休息视频)) {
            list.add(new GankItemTitle(results.休息视频.get(0)));
            list.addAll(results.休息视频);
        }
        return list;
    }

    private void processGirls(List<Object> list) {
        if (AppUtil.isEmpty(list) || GankApp.girls == null) {
            GankApp.girls = new ArrayList<>();
            return;
        }
        GankApp.girls.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof GankItemGirl) {
                GankItemGirl girl = (GankItemGirl) list.get(i);
                GankApp.girls.add(TextUtils.isEmpty(girl.url) ? "" : girl.url);
            }
        }
    }

}
