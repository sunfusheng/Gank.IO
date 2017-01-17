package com.sunfusheng.gank.model;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class GankItemTitle extends GankItem {

    public GankItemTitle() {
    }

    public GankItemTitle(GankItem item) {
        type = item.type;
        desc = item.desc;
        who = item.who;
        url = item.url;
        images = item.images;
        createdAt = item.createdAt;
        publishedAt = item.publishedAt;
    }

}
