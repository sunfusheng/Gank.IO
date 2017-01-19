package com.sunfusheng.gank;

import com.sunfusheng.gank.viewprovider.GankItemGirlViewProvider;
import com.sunfusheng.gank.viewprovider.GankItemTitleViewProvider;
import com.sunfusheng.gank.viewprovider.GankItemViewProvider;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.model.GankItemTitle;
import com.sunfusheng.gank.widget.MultiType.GlobalMultiTypePool;

/**
 * Created by sunfusheng on 2017/1/16.
 */
public class MultiTypeInitializer {

    static void init() {
        GlobalMultiTypePool.register(GankItem.class, new GankItemViewProvider());
        GlobalMultiTypePool.register(GankItemGirl.class, new GankItemGirlViewProvider());
        GlobalMultiTypePool.register(GankItemTitle.class, new GankItemTitleViewProvider());
    }
}
