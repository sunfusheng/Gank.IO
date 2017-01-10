package com.sunfusheng.gank;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by sunfusheng on 2017/1/5.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init("RxGank").logLevel(BuildConfig.DEBUG? LogLevel.FULL:LogLevel.NONE);
    }

}
