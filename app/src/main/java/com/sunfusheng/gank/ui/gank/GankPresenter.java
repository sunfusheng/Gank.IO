package com.sunfusheng.gank.ui.gank;

import android.text.TextUtils;

import com.sunfusheng.gank.GankApp;
import com.sunfusheng.gank.http.Api;
import com.sunfusheng.gank.model.GankDay;
import com.sunfusheng.gank.model.GankDayResults;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.model.GankItemTitle;
import com.sunfusheng.gank.model.RequestParams;
import com.sunfusheng.gank.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankPresenter implements GankContract.Presenter {

    private GankView mView;
    private List<Object> mList;
    private RequestParams mRequestParams;
    protected Subject<Void, Void> lifecycle = new SerializedSubject<>(PublishSubject.create());

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
        lifecycle.onCompleted();
        if (mView != null) {
            mView.onDetach();
        }
    }

    @Override
    public void onRefresh() {
        if (Utils.isEmpty(mList)) {
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
                    if (Utils.notEmpty(list)) {
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
            if (Utils.notEmpty(mList)) {
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
        if (Utils.notEmpty(results.福利)) list.addAll(results.福利);
        if (Utils.notEmpty(results.Android)) {
            list.add(new GankItemTitle(results.Android.get(0)));
            list.addAll(results.Android);
        }
        if (Utils.notEmpty(results.iOS)) {
            list.add(new GankItemTitle(results.iOS.get(0)));
            list.addAll(results.iOS);
        }
        if (Utils.notEmpty(results.App)) {
            list.add(new GankItemTitle(results.App.get(0)));
            list.addAll(results.App);
        }
        if (Utils.notEmpty(results.瞎推荐)) {
            list.add(new GankItemTitle(results.瞎推荐.get(0)));
            list.addAll(results.瞎推荐);
        }
        if (Utils.notEmpty(results.休息视频)) {
            list.add(new GankItemTitle(results.休息视频.get(0)));
            list.addAll(results.休息视频);
        }
        return list;
    }

    private void processGirls(List<Object> list) {
        if (Utils.isEmpty(list) || GankApp.girls == null) {
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
