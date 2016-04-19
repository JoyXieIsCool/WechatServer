package com.wechatserver.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author: Joey
 * Date: 2016/4/19
 * Time: 10:41
 * IO 处理工具类
 */
public class IOUtil {

    public static void close(Closeable closeable) {
        if (null == closeable) {
            return;
        } else {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
