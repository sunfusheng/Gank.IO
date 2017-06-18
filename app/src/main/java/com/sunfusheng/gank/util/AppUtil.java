package com.sunfusheng.gank.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class AppUtil {

    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    // 判断网络是否可用
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // WiFi是否连接
    public static boolean isWiFiAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    // 获取当前应用的版本号
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            String version = packInfo.versionName;
            if (!TextUtils.isEmpty(version)) {
                return "V" + version;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "V1.0";
    }

    // 获取当前应用的版本号
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // 去掉重复点击
    public static void singleClick(View view, Consumer consumer) {
        RxView.clicks(view)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(consumer, Throwable::printStackTrace);
    }

}
