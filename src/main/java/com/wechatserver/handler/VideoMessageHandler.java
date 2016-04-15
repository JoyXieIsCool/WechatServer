package com.wechatserver.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/15
 * Time: 16:58
 * 微信视频消息的处理类，目前不做任何处理
 */
@Component
public class VideoMessageHandler extends PushNotificationHandler {

    static {
        // 将子类的映射关系添加到父类中
        addHandler("video", new VideoMessageHandler());
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
