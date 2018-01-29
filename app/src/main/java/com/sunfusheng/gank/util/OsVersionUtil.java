package com.sunfusheng.gank.util;


import android.os.Build;

/**
 * @author sunfusheng on 2018/1/29.
 */
public class OsVersionUtil {

    public static boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean hasIceCreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= 15;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static boolean hasKitkatWatch() {
        return Build.VERSION.SDK_INT >= 20;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean hasLollipopMR1() {
        return Build.VERSION.SDK_INT >= 22;
    }

    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static boolean hasN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static boolean hasNMR1() {
        return Build.VERSION.SDK_INT >= 25;
    }

    public static boolean hasO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean hasOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

}
