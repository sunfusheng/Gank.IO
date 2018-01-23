package com.sunfusheng.gank.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author sunfusheng on 2018/1/23.
 */
public class CloseUtil {

    public static void close(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable == null) continue;
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable == null) continue;
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
