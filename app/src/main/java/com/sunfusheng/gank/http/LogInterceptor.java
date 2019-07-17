package com.sunfusheng.gank.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author by sunfusheng on 2017/1/22.
 */
public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Log.d("api-request: ", request.url().toString());

        Response response = chain.proceed(request);
        if (response == null || response.body() == null) {
            return response;
        }

        MediaType contentType = response.body().contentType();
        String content = response.body().string();
        Log.d("api-response: ", content);

        return response.newBuilder()
                .body(ResponseBody.create(contentType, content))
                .build();
    }
}
