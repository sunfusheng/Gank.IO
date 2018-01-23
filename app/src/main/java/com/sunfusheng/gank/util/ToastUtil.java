package com.sunfusheng.gank.util;

import android.annotation.SuppressLint;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by sunfusheng on 17/2/22.
 */
public class ToastUtil {

    private static Toast toast;

    public static void toast(@StringRes int resId) {
        toastIgnoreEmpty(AppUtil.getApp().getString(resId), true);
    }

    public static void toast(String msg) {
        toastIgnoreEmpty(msg, true);
    }

    public static void toastLong(@StringRes int resId) {
        toastIgnoreEmpty(AppUtil.getApp().getString(resId), false);
    }

    public static void toastLong(String msg) {
        toastIgnoreEmpty(msg, false);
    }

    private static void toastIgnoreEmpty(String msg, boolean isShort) {
        if (!TextUtils.isEmpty(msg)) {
            toast(msg, isShort);
        }
    }

    @SuppressLint("ShowToast")
    public static void toast(String msg, boolean isShort) {
        if (AppUtil.isAppForeground()) {
            int duration = isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
            if (toast == null) {
                toast = Toast.makeText(AppUtil.getApp(), msg, duration);
            } else {
                toast.setText(msg);
                toast.setDuration(duration);
            }
            toast.show();
        }
    }
}