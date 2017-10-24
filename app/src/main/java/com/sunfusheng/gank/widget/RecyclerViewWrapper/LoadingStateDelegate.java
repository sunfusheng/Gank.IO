package com.sunfusheng.gank.widget.RecyclerViewWrapper;

import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewStub;

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

    private View viewHolder[] = new View[4];
    private ViewStub viewStubHolder[] = new ViewStub[4];

    public LoadingStateDelegate(View normalView, View loadingView, ViewStub errorStub, ViewStub emptyStub) {
        viewHolder[0] = normalView;
        viewHolder[1] = loadingView;
        viewStubHolder[2] = errorStub;
        viewStubHolder[3] = emptyStub;
    }

    public View setViewState(@STATE int state) {
        if (state < 0 || state >= viewHolder.length) {
            return null;
        }

        for (View v : viewHolder) {
            if (v == null) {
                continue;
            }
            v.setVisibility(View.GONE);
        }

        if (viewHolder[state] == null) {
            if (viewStubHolder[state] != null && viewStubHolder[state].getParent() != null) {
                viewHolder[state] = viewStubHolder[state].inflate();
            }
        }

        if (viewHolder[state] != null) {
            viewHolder[state].setVisibility(View.VISIBLE);
        }

        return viewHolder[state];
    }
}
