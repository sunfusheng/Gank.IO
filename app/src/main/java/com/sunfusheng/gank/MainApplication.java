package com.sunfusheng.gank;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

/**
 * @author by sunfusheng on 2017/1/5.
 */
public class MainApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static Typeface songTi; // 宋体
    public static ArrayList<String> girls = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .tag(getString(R.string.app_name))
                .showThreadInfo(false)
                .build();
        AndroidLogAdapter logAdapter = new AndroidLogAdapter(strategy);
        Logger.addLogAdapter(logAdapter);

        Schedulers.io().createWorker().schedule(() -> songTi = Typeface.createFromAsset(getAssets(), "SongTi.TTF"));
    }
}
