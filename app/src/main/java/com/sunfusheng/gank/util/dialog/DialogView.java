package com.sunfusheng.gank.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.sunfusheng.gank.R;

/**
 * Created by sunfusheng on 2017/5/17.
 */
public class DialogView {

    public static final int ANIMATION_STYLE_SLIDE = 1;
    public static final int ANIMATION_STYLE_SCALE = 2;
    public static final int ANIMATION_STYLE_FADE = 3;

    protected Context mContext;
    protected View mView;
    private Dialog mDialog;
    private Window mWindow;
    private OnDismissListener mOnDialogDismissListener;

    public DialogView(Context context, int layoutId) {
        this.mContext = context;
        this.mView = loadLayout(layoutId);
        initDialog();
    }

    public DialogView(Context context, View view) {
        this.mContext = context;
        this.mView = view;
        initDialog();
    }

    private View loadLayout(int layoutId) {
        View v = LayoutInflater.from(mContext).inflate(layoutId, null);
        onLoadLayout(v);
        return v;
    }

    public void onLoadLayout(View view) {

    }

    private void initDialog() {
        mDialog = new Dialog(mContext, R.style.dialog_view_theme);
        mWindow = mDialog.getWindow();
        if (mWindow == null || mView == null) {
            throw new RuntimeException("mWindow or mView is null");
        }
        setGravity(Gravity.CENTER);
        setAnimationStyle(ANIMATION_STYLE_SLIDE);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(mView);
    }

    public void setAnimationStyle(int style) {
        switch (style) {
            case ANIMATION_STYLE_SLIDE:
                mWindow.setWindowAnimations(R.style.dialog_slide_animation);
                break;
            case ANIMATION_STYLE_SCALE:
                mWindow.setWindowAnimations(R.style.dialog_scale_animation);
                break;
            case ANIMATION_STYLE_FADE:
                mWindow.setWindowAnimations(R.style.dialog_fade_animation);
                break;
        }
    }

    public void setGravity(int gravity) {
        if (mWindow != null) {
            mWindow.setGravity(gravity);
        }
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setFullWidth(boolean isFull) {
        if (isFull && mDialog != null) {
            LayoutParams lp = mWindow.getAttributes();
            lp.width = LayoutParams.MATCH_PARENT;
            lp.height = LayoutParams.WRAP_CONTENT;
        }
    }

    public void setFullScreen(boolean isFull) {
        if (isFull && mDialog != null) {
            LayoutParams lp = mWindow.getAttributes();
            lp.width = LayoutParams.MATCH_PARENT;
            lp.height = LayoutParams.MATCH_PARENT;
        }
    }

    public void setCancelable(boolean cancel) {
        if (mDialog != null) {
            mDialog.setCancelable(cancel);
        }
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        if (mDialog != null) {
            mDialog.setCanceledOnTouchOutside(cancel);
        }
    }

    public OnDismissListener getOnDialogDismissListener() {
        return mOnDialogDismissListener;
    }

    public void setOnDialogDismissListener(OnDismissListener onDialogDismissListener) {
        this.mOnDialogDismissListener = onDialogDismissListener;
        if (mDialog != null) {
            mDialog.setOnDismissListener(mOnDialogDismissListener);
        }
    }
}
