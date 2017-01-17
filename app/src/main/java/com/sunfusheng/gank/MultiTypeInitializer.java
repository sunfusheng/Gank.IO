package com.sunfusheng.gank;

import com.sunfusheng.gank.adapter.TestViewProvider;
import com.sunfusheng.gank.model.TestEntity;
import com.sunfusheng.gank.widget.MultiType.GlobalMultiTypePool;

/**
 * Created by sunfusheng on 2017/1/16.
 */
public class MultiTypeInitializer {

    static void init() {
        GlobalMultiTypePool.register(TestEntity.class, new TestViewProvider());
    }
}
