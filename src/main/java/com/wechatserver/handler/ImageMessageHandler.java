package com.wechatserver.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/14.
 * 微信图片消息的处理类
 */
@Component
public class ImageMessageHandler extends PushNotificationHandler{
    @Override
    public String handleNotification(Map<String, Object> msg) {
        // 对于图片信息目前不做任何处理
        return null;
    }

    @Override
    protected PushNotificationHandler getCurrentHandler(Map<String, Object> msg) {
        return this;
    }
}
