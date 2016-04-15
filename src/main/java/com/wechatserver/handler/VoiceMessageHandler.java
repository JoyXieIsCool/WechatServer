package com.wechatserver.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/15
 * Time: 16:50
 * 微信语音消息的处理类，目前不做任何处理
 */
@Component
public class VoiceMessageHandler extends PushNotificationHandler{

    static {
        // 将子类映射关系添加到父类中
        addHandler("voice", new VoiceMessageHandler());
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
