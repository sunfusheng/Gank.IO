package com.sunfusheng.gank;

import android.app.Application;
import android.graphics.Typeface;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by sunfusheng on 2017/1/5.
 */
public class GankApp extends Application {

    public static Application application;
    public static Typeface songTi; // 宋体
    public static ArrayList<String> girls = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .tag(getString(R.string.app_name))
                .showThreadInfo(false)
                .build();
        AndroidLogAdapter logAdapter = new AndroidLogAdapter(strategy);
        Logger.addLogAdapter(logAdapter);

        Schedulers.io().createWorker().schedule(() -> songTi = Typeface.createFromAsset(getAssets(), "SongTi.TTF"));
        Initializer.init();
    }
}
