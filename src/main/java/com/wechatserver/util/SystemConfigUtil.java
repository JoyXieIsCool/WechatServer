package com.wechatserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Author: Joey
 * Date: 2016/4/19
 * Time: 10:28
 * 获取项目中的配置工具类
 */
public class SystemConfigUtil {
    private static Logger logger = LoggerFactory.getLogger(SystemConfigUtil.class);

    private static final String WECHAT_CONFIG_PATH = "/wechat.properties";
    private static Properties wechatProperties = new Properties();

    static {
        // 加载微信配置文件，如果有多个文件则都在这里初始化
        loadProperties(WECHAT_CONFIG_PATH, wechatProperties);
    }


    /**
     * 根据路径加载 properties 文件
     * @param path
     * @param props
     */
    private static void loadProperties(String path, Properties props) {
        InputStream inputStream = null;
        InputStreamReader inReader = null;

        try {
            inputStream = SystemConfigUtil.class.getResourceAsStream(path);
            inReader = new InputStreamReader(inputStream, "utf-8");
            props.load(inputStream);
        } catch (Exception e) {
            logger.error("Failed to load properties file: " + path, e);
        } finally {
            IOUtil.close(inReader);
            IOUtil.close(inputStream);
        }
    }

    public static String getWechatProperty(String key) {
        return wechatProperties.getProperty(key);
    }


}
