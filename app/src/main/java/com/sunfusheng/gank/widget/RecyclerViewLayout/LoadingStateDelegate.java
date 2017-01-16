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

    @IntDef({LOADING_STATE.STATE_LOADING, LOADING_STATE.STATE_LOAD_SUCCEED, LOADING_STATE.STATE_LOAD_FAILED, LOADING_STATE.STATE_LOAD_EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LOADING_STATE {
        int STATE_LOADING = 0;
        int STATE_LOAD_SUCCEED = 1;
        int STATE_LOAD_FAILED = 2;
        int STATE_LOAD_EMPTY = 3;
    }

    private View viewHolder[] = new View[4];
    private ViewStub viewStubHolder[] = new ViewStub[4];

    public LoadingStateDelegate(View normalView,
                                View loadingView,
                                ViewStub errorViewStub,
                                ViewStub emptyViewStub) {
        viewHolder[0] = loadingView;
        viewHolder[1] = normalView;
        viewStubHolder[2] = errorViewStub;
        viewStubHolder[3] = emptyViewStub;
    }

    public View setViewState(@LOADING_STATE int viewState) {
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
