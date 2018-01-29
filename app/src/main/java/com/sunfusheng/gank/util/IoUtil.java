package com.sunfusheng.gank.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author sunfusheng on 2018/1/23.
 */
public class IoUtil {

    public static void close(final Closeable... closeables) {
        if (CollectionUtil.isEmpty(closeables)) {
            return;
        }

        for (Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }

            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeQuietly(final Closeable... closeables) {
        if (CollectionUtil.isEmpty(closeables)) {
            return;
        }

        for (Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }

            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
