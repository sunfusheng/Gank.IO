package com.sunfusheng.gank.model;

import java.util.List;

/**
 * @author by sunfusheng on 2017/1/17.
 */
public class GankDay extends BaseEntity {

    public transient boolean error;
    public List<String> category;
    public GankDayResults results;

    @Override
    public String toString() {
        return "GankDay{" +
                "error=" + error +
                ", category=" + category +
                ", results=" + results +
                '}';
    }
}
