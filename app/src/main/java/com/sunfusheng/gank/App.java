package com.sunfusheng.gank;

import android.app.Application;
import android.graphics.Typeface;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by sunfusheng on 2017/1/5.
 */
public class App extends Application {

    public static Typeface songTiTf; // 宋体

    @Override
    public void onCreate() {
        super.onCreate();

        songTiTf = Typeface.createFromAsset(getAssets(), "Songti-SC-Black.TTF");

        Logger.init("RxGank").setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);

        MultiTypeInitializer.init();
    }

}
