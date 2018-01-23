package com.sunfusheng.gank.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Stack;

/**
 * @author sunfusheng on 2018/1/23.
 */
@SuppressLint("StaticFieldLeak")
public class AppUtil {

    private static Application application;
    private static Context context;
    private static WeakReference<Activity> topActivityWeakRef;
    private static Stack<Activity> activityStack = new Stack<Activity>();

    private static ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            activityStack.push(activity);
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityStack.remove(activity);
        }
    };

    public static Application getApp() {
        if (application != null) {
            return application;
        }
        throw new NullPointerException("AppUtil should be initialized first!");
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("AppUtil should be initialized first!");
    }

    public static void init(@NonNull final Application app) {
        application = app;
        context = app.getApplicationContext();
        app.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public static void exitApp() {
        while (activityStack.size() > 0) {
            Activity activity = activityStack.pop();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
        System.exit(0);
    }

    private static void setTopActivity(final Activity activity) {
        if (activity == null) return;
        if (topActivityWeakRef == null || !activity.equals(topActivityWeakRef.get())) {
            topActivityWeakRef = new WeakReference<>(activity);
        }
    }

    public static Activity getTopActivity() {
        if (topActivityWeakRef != null) {
            topActivityWeakRef.get();
        }
        return null;
    }

    public static String getAppPackageName() {
        return getApp().getPackageName();
    }

    public static String getAppName() {
        return getAppName(getApp().getPackageName());
    }

    public static String getAppName(final String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getAppIcon() {
        return getAppIcon(getApp().getPackageName());
    }

    public static Drawable getAppIcon(final String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAppPath() {
        return getAppPath(getApp().getPackageName());
    }

    public static String getAppPath(final String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAppVersionName() {
        return getAppVersionName(getApp().getPackageName());
    }

    public static String getAppVersionName(final String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getAppVersionCode() {
        return getAppVersionCode(getApp().getPackageName());
    }

    public static int getAppVersionCode(final String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager) getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        List<ActivityManager.RunningAppProcessInfo> appProcesses = manager.getRunningAppProcesses();
        if (CollectionUtil.isEmpty(appProcesses)) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : appProcesses) {
            if (processInfo == null) continue;
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return processInfo.processName.equals(getApp().getPackageName());
            }
        }
        return false;
    }

}
