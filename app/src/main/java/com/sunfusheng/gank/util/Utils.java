package com.sunfusheng.gank.util;

import java.util.List;

/**
 * Created by sunfusheng on 2017/1/17.
 */
public class Utils {

    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }
}
