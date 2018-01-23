package com.sunfusheng.gank.ui.gank;

import android.text.TextUtils;

import com.sunfusheng.gank.App;
import com.sunfusheng.gank.http.Api;
import com.sunfusheng.gank.model.GankDay;
import com.sunfusheng.gank.model.GankDayResults;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.model.GankItemTitle;
import com.sunfusheng.gank.model.RequestParams;
import com.sunfusheng.gank.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author by sunfusheng on 2017/1/13.
 */
public class GankPresenter implements GankContract.Presenter {

    private GankView mView;
    private List<Object> mList;
    private RequestParams mRequestParams;
    private Subject<Object> lifecycle = PublishSubject.create().toSerialized();

    GankPresenter(GankView view) {
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
    public void onLoad() {
        if (CollectionUtil.isEmpty(mList)) {
            mView.onLoading();
        }
        mList = new ArrayList<>();
        mRequestParams = new RequestParams();
        getGankDayList(false);
    }

    @Override
    public void onLoadMore() {
        getGankDayList(true);
    }

    private void getGankDayList(final boolean isLoadMore) {
        Api.getInstance().getApiService().getGankDay(mRequestParams.year, mRequestParams.month, mRequestParams.day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(gankDay -> gankDay != null && gankDay.results != null)
                .map(this::flatGankDay2List)
                .takeUntil(lifecycle)
                .subscribe(list -> {
                    if (CollectionUtil.isNotEmpty(list)) {
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
            if (CollectionUtil.isNotEmpty(mList)) {
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
        if (CollectionUtil.isNotEmpty(results.福利)) {
            list.addAll(results.福利);
        }
        if (CollectionUtil.isNotEmpty(results.Android)) {
            list.add(new GankItemTitle(results.Android.get(0)));
            list.addAll(results.Android);
        }
        if (CollectionUtil.isNotEmpty(results.iOS)) {
            list.add(new GankItemTitle(results.iOS.get(0)));
            list.addAll(results.iOS);
        }
        if (CollectionUtil.isNotEmpty(results.App)) {
            list.add(new GankItemTitle(results.App.get(0)));
            list.addAll(results.App);
        }
        if (CollectionUtil.isNotEmpty(results.瞎推荐)) {
            list.add(new GankItemTitle(results.瞎推荐.get(0)));
            list.addAll(results.瞎推荐);
        }
        if (CollectionUtil.isNotEmpty(results.休息视频)) {
            list.add(new GankItemTitle(results.休息视频.get(0)));
            list.addAll(results.休息视频);
        }
        return list;
    }

    private void processGirls(List<Object> list) {
        if (CollectionUtil.isEmpty(list) || App.girls == null) {
            App.girls = new ArrayList<>();
            return;
        }
        App.girls.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof GankItemGirl) {
                GankItemGirl girl = (GankItemGirl) list.get(i);
                App.girls.add(TextUtils.isEmpty(girl.url) ? "" : girl.url);
            }
        }
    }

}
