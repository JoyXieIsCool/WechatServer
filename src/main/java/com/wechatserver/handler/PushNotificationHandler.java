package com.wechatserver.handler;

import com.wechatserver.constant.MessageConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/13.
 * 微信推送通知处理器父类，是所有处理微信事件推送的类、被动回复微信消息的类的根类
 */
public class PushNotificationHandler {

    private static final Map<String, PushNotificationHandler> handlerMap = new HashMap<String, PushNotificationHandler>();


    /**
     * 子类重写该方法，添加具体的处理逻辑
     * @param msg 已经被解析过的XML
     * @return 返回给微信服务器的文本，XML格式，交由子类处理。如果无须返回给微信服务器则返回 null
     */
    public String handleNotification(Map<String, Object> msg) {
        return null;
    }

    /**
     * 子类根据解析后的 XML 进行判断，一般是返回this即可，但如果子类还维护了一个映射表的话则需要添加相应的逻辑
     * @param msg
     * @return
     */
    protected PushNotificationHandler getCurrentHandler(Map<String, Object> msg) {
        //return this;
        // 其实这里返回this也可以调用子类的 handleNotification() 方法，但是意义不够直接，所以交由子类去返回this或其它对象，若子类不实现则返回空实现
        return new PushNotificationHandler();
    }

    /**
     * 子类调用该方法将自己和 msgType 的映射添加到父类
     * @param msgType
     * @param handler
     */
    protected static void addHandler(String msgType, PushNotificationHandler handler) {
        handlerMap.put(msgType, handler);
    }

    public static PushNotificationHandler getHandler(Map<String, Object> msg) {
        // 首先根据 msgType 查找handler
        PushNotificationHandler handler = handlerMap.get(msg.get(MessageConstant.MSG_TYPE));
        if (null != handler) {
            /**
             * 如果 handler 不为空，则调用它的getCurrentHandler方法获取handler。
             * 这样做是因为对于普通的消息，handlerMap 根据 msgType 就可以查找到一个 handler，而对于
             * 事件推送，它们的 msgType 都是 event，所以还需要让子类根据 Event 标签的值来获取具体的handler
             */
            return handler.getCurrentHandler(msg);
        } else {
            // 返回空的处理器，不做任何处理
            return new PushNotificationHandler();
        }
    }
}
