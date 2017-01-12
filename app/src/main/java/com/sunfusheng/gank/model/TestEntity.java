package com.sunfusheng.gank.model;

/**
 * Created by sunfusheng on 2017/1/12.
 */

public class TestEntity implements IMoreData {

    public String desc;

    public TestEntity() {
    }

    public TestEntity(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
