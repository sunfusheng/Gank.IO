package com.sunfusheng.gank.widget.RecyclerViewWrapper;

import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author by sunfusheng on 2017/1/16.
 */
public class LoadingStateDelegate {

    @IntDef({STATE.SUCCEED, STATE.LOADING, STATE.ERROR, STATE.EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int SUCCEED = 0;
        int LOADING = 1;
        int ERROR = 2;
        int EMPTY = 3;
    }

    private View views[] = new View[4];

    public LoadingStateDelegate(View normalView, View loadingView, View errorView, View emptyView) {
        views[0] = normalView;
        views[1] = loadingView;
        views[2] = errorView;
        views[3] = emptyView;
    }

    public View setLoadingState(@STATE int state) {
        if (state < 0 || state >= views.length) {
            return null;
        }

        for (View view : views) {
            if (view == null) {
                continue;
            }
            view.setVisibility(View.GONE);
        }

        if (views[state] != null) {
            views[state].setVisibility(View.VISIBLE);
        }

        return views[state];
    }
}
