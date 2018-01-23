package com.sunfusheng.gank.util;

import java.util.Collection;

/**
 * @author sunfusheng on 2018/1/23.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

}
