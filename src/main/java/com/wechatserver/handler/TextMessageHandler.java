package com.wechatserver.handler;

import com.wechatserver.constant.MessageConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/14.
 * 处理微信服务器发送过来的文本消息
 */
@Component
public class TextMessageHandler extends PushNotificationHandler{

    static {
        // 将子类的映射关系添加到父类中保存
        addHandler("text", new TextMessageHandler());
    }

    private static final String REPLY_TEMPLATE = "<xml>\n" +
            "<ToUserName><![CDATA[%s]]></ToUserName>\n" +
            "<FromUserName><![CDATA[%s]]></FromUserName>\n" +
            "<CreateTime>%d</CreateTime>\n" +
            "<MsgType><![CDATA[text]]></MsgType>\n" +
            "<Content><![CDATA[%s]]></Content>\n" +
            "</xml>";

    @Override
    protected PushNotificationHandler getCurrentHandler(Map<String, Object> msg) {
        return this;
    }

    @Override
    public String handleNotification(Map<String, Object> msg) {
        String reply = String.format(REPLY_TEMPLATE,
                                     msg.get(MessageConstant.FROM_USER_NAME),
                                     msg.get(MessageConstant.TO_USER_NAME),
                                     System.currentTimeMillis(),
                                     "Congratulations! 成功啦~");
        return reply;
    }
}
