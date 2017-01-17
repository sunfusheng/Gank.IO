package com.sunfusheng.gank.http;

import com.sunfusheng.gank.model.GankDay;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public interface ApiService {

    @GET("day/{year}/{month}/{day}")
    Observable<GankDay> getGankDay(@Path("year")int year, @Path("month")int month, @Path("day")int day);
}
