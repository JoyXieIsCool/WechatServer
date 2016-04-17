package com.wechatserver.handler;

import com.wechatserver.constant.EventConstant;
import com.wechatserver.constant.MessageConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/16
 * Time: 13:43
 * 推送事件处理器的基类，用于处理各种微信事件推送的类都必须继承该类
 */
public class PushEventHandler extends PushNotificationHandler {
    private static final Map<String, PushNotificationHandler> eventHandlerMap = new HashMap<String, PushNotificationHandler>();

    protected static void addEventHandler(String eventType, PushEventHandler handler) {
        eventHandlerMap.put(eventType, handler);
    }

    /**
     * 父类只根据 msgType 为 event 获取到本类的对象，但是本类对象需要根据 Event 类型来查找对应的处理器，
     * 这个属于第二层的映射关系，因此由本类来维护而不是父类
     * @param msg
     * @return
     */
    @Override
    protected PushNotificationHandler getCurrentHandler(Map<String, Object> msg) {
        return eventHandlerMap.get(msg.get(EventConstant.EVENT));
    }
}
