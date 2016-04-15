package com.wechatserver.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/15
 * Time: 17:47
 * 处理微信小视频消息，暂不做任何处理
 */
@Component
public class ShortvideoMessageHandler extends PushNotificationHandler {

    static {
        // 将子类的映射添加到父类中
        addHandler("shortvideo", new ShortvideoMessageHandler());
    }

    @Override
    public String handleNotification(Map<String, Object> msg) {
        return null;
    }

    @Override
    protected PushNotificationHandler getCurrentHandler(Map<String, Object> msg) {
        return this;
    }
}
