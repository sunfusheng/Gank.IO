package com.sunfusheng.gank.widget.RecyclerViewLayout;

import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewStub;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sunfusheng on 2017/1/16.
 */
public class LoadingStateDelegate {

    @IntDef({STATE.LOADING, STATE.SUCCEED, STATE.ERROR, STATE.EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int LOADING = 0;
        int SUCCEED = 1;
        int ERROR = 2;
        int EMPTY = 3;
    }

    private View viewHolder[] = new View[4];
    private ViewStub viewStubHolder[] = new ViewStub[4];

    public LoadingStateDelegate(View normalView, View loadingView, ViewStub errorStub, ViewStub emptyStub) {
        viewHolder[0] = loadingView;
        viewHolder[1] = normalView;
        viewStubHolder[2] = errorStub;
        viewStubHolder[3] = emptyStub;
    }

    public View setViewState(@STATE int viewState) {
        if (viewState < 0 || viewState >= viewHolder.length) {
            return null;
        }

        for (View v : viewHolder) {
            if (v == null) {
                continue;
            }
            v.setVisibility(View.GONE);
        }

        if (viewHolder[viewState] == null) {
            if (viewStubHolder[viewState] != null && viewStubHolder[viewState].getParent() != null) {
                viewHolder[viewState] = viewStubHolder[viewState].inflate();
            }
        }

        if (viewHolder[viewState] != null) {
            viewHolder[viewState].setVisibility(View.VISIBLE);
        }

        return viewHolder[viewState];
    }

    public void setEmptyView(View view){
        viewHolder[3] = view;
    }
}
