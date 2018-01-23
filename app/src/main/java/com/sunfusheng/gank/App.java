package com.sunfusheng.gank;

import android.app.Application;
import android.graphics.Typeface;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sunfusheng.gank.util.AppUtil;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

/**
 * @author by sunfusheng on 2017/1/5.
 */
public class App extends Application {

    public static Typeface songTi; // 宋体
    public static ArrayList<String> girls = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.init(this);

        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .tag(getString(R.string.app_name))
                .showThreadInfo(false)
                .build();
        AndroidLogAdapter logAdapter = new AndroidLogAdapter(strategy);
        Logger.addLogAdapter(logAdapter);

        Schedulers.io().createWorker().schedule(() -> songTi = Typeface.createFromAsset(getAssets(), "SongTi.TTF"));
    }
}
