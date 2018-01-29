package com.sunfusheng.gank.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author sunfusheng on 2018/1/23.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return null == array || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return null == array || array.length == 0;
    }

    public static int getSize(Collection<?> collection) {
        return null == collection ? 0 : collection.size();
    }

}
