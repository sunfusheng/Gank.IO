package com.sunfusheng.gank;

import android.app.Application;
import android.graphics.Typeface;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 2017/1/5.
 */
public class GankApp extends Application {

    public static Typeface songTi; // 宋体
    public static List<String> girls;

    @Override
    public void onCreate() {
        super.onCreate();
        girls = new ArrayList<>();
        Logger.init("RxGank").setLogLevel(BuildConfig.isDebug ? LogLevel.FULL : LogLevel.NONE);

        songTi = Typeface.createFromAsset(getAssets(), "SongTi.TTF");
        MultiTypeInitializer.init();
    }

}
