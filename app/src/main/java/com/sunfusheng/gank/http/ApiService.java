package com.sunfusheng.gank.http;

import com.sunfusheng.gank.model.GankDay;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * @author by sunfusheng on 2017/1/17.
 */
public interface ApiService {

    @Headers("Cache-Control: public, max-age=3600")
    @GET("day/{year}/{month}/{day}")
    Observable<Response<GankDay>> getGankDay(@Path("year") int year, @Path("month") int month, @Path("day") int day);

}
