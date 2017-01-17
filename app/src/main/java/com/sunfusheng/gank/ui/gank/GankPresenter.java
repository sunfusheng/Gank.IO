package com.sunfusheng.gank.ui.gank;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.sunfusheng.gank.http.Api;
import com.sunfusheng.gank.model.GankDay;
import com.sunfusheng.gank.model.GankDayResults;
import com.sunfusheng.gank.model.GankItemTitle;
import com.sunfusheng.gank.model.RequestInfo;
import com.sunfusheng.gank.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankPresenter implements GankContract.Presenter {

    private Context mContext;
    private GankView mView;
    private List<Object> mList;
    private RequestInfo mRequestInfo;

    public GankPresenter(Context context, GankView mView) {
        this.mContext = context;
        this.mView = mView;
    }

    @Override
    public void init() {
        if (mView != null) {
            mView.setPresenter(this);
            mView.onAttach();
        }
        mList = new ArrayList<>();
        mRequestInfo = new RequestInfo();
    }

    @Override
    public void unInit() {
        if (mView != null) {
            mView.onDetach();
        }
    }

    @Override
    public void loadList() {
        mView.onLoading();
        getGankDayList();
    }

    private void getGankDayList() {
        Api.getInstance().getApiService().getGankDay(mRequestInfo.year, mRequestInfo.month, mRequestInfo.day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(gankDay -> gankDay != null)
                .filter(gankDay -> gankDay.results != null)
                .doOnNext(gankDay -> Logger.d("log-data", gankDay.toString()))
                .map(this::flatGankDay2List)
                .subscribe(list -> {
                    if (Utils.notEmpty(list)) {
                        Logger.d("log-data", mRequestInfo.toString());
                        mList.addAll(list);
                        mRequestInfo.onSuccess();
                    } else {
                        mRequestInfo.onEmpty();
                    }

                    if (!mRequestInfo.isComplete()) {
                        getGankDayList();
                    } else {
                        if (Utils.notEmpty(mList)) {
                            mView.onSuccess(mList);
                        } else {
                            mView.onEmpty();
                        }
                    }
                }, e -> {
                    mRequestInfo.onError();
                    if (!mRequestInfo.isComplete()) {
                        getGankDayList();
                    } else {
                        if (Utils.notEmpty(mList)) {
                            mView.onSuccess(mList);
                        } else {
                            mView.onError();
                        }
                    }
                });
    }

    @Override
    public void loadMoreList() {
        getGankDayList();
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

}
