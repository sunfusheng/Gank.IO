package com.sunfusheng.gank.http;

import com.sunfusheng.gank.BuildConfig;
import com.sunfusheng.gank.Constants;
import com.sunfusheng.gank.GankApp;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class Api {

    private static ApiService mApiService;

    // 连接超时时间，默认20秒
    private static final int CONNECT_TIMEOUT = 20;

    private static class Holder {
        private static Api instance = new Api();
    }

    private Api() {
        init();
    }

    public static Api getInstance() {
        return Holder.instance;
    }

    private void init() {
        File cacheFile = new File(GankApp.application.getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); // 20M

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache);

        if (BuildConfig.isDebug) {
            builder.addInterceptor(new LogInterceptor());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        if (mApiService == null) {
            init();
        }
        return mApiService;
    }

}
