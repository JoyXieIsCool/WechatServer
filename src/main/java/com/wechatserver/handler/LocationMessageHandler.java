package com.wechatserver.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/15
 * Time: 17:50
 * 处理微信用户发送过来的地理消息，暂不做任何处理
 */
@Component
public class LocationMessageHandler extends PushNotificationHandler {

    static {
        // 将子类的映射关系添加到父类
        addHandler("location", new LocationMessageHandler());
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
