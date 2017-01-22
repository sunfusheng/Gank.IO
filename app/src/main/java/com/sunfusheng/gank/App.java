package com.sunfusheng.gank;

import android.app.Application;
import android.graphics.Typeface;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by sunfusheng on 2017/1/5.
 */
public class App extends Application {

    public static Typeface songTi; // 宋体

    @Override
    public void onCreate() {
        super.onCreate();

        songTi = Typeface.createFromAsset(getAssets(), "SongTi.TTF");

        Logger.init("RxGank").setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);

        MultiTypeInitializer.init();
    }

}
