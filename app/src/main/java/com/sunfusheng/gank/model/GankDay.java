package com.sunfusheng.gank.model;

import java.util.List;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class GankDay extends BaseEntity {

    public List<String> category;
    public GankDayResults results;

    @Override
    public String toString() {
        return "GankDay{" +
                "category=" + category +
                ", results=" + results +
                '}';
    }
}
