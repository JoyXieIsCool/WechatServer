package com.wechatserver.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/15
 * Time: 17:53
 * 处理微信用户发送过来的链接，暂不做任何处理
 */
@Component
public class LinkMessageHandler extends PushNotificationHandler {

    static {
        // 将子类的映射关系添加到父类
        addHandler("link", new LinkMessageHandler());
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
