package com.wechatserver.controller;

import com.wechatserver.constant.WechatConfigConstant;
import com.wechatserver.handler.PushNotificationHandler;
import com.wechatserver.util.SystemConfigUtil;
import com.wechatserver.util.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 做微信接入验证的Controller
 */
@Controller
@RequestMapping("/")
public class WechatDispatchController {

    private static final String TOKEN = SystemConfigUtil.getWechatProperty(WechatConfigConstant.TOKEN);
    private static final Logger logger = LoggerFactory.getLogger(WechatDispatchController.class);


    /**
     * 校验签名，处理微信接入
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value="washcar", method = RequestMethod.GET)
    @ResponseBody
    public String verify(String signature, String timestamp, String nonce, String echostr) {
        logger.info("signature:" + signature);
        logger.info("timestamp:" + timestamp);
        logger.info("nonce:    " +nonce);
        logger.info("echostr:  " + echostr);

        String[] tempArr = new String[]{timestamp, nonce, TOKEN};
        Arrays.sort(tempArr);
        String conbineString = tempArr[0] + tempArr[1] + tempArr[2];
        try {
            byte[] digest = MessageDigest.getInstance("SHA-1").digest(conbineString.getBytes("utf-8"));
            String myEncryptResult = byteToHexString(digest);
            logger.info("myEncryptResult:" + myEncryptResult);
            if(myEncryptResult.equals(signature))
                return echostr;
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            e.printStackTrace();
        }

        return "false";
    }

    @RequestMapping(value="washcar", method = RequestMethod.POST)
    public void handleMessage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        logger.info("Request URL is : " + req.getRequestURL().toString());
        logger.info("Query String is: " + req.getQueryString());

        BufferedReader reader = req.getReader();
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        logger.info("====== start receive message =======");
        logger.info("\n" + buffer.toString());
        logger.info("====== end receive message =======");

        Map<String, Object> msg = XMLUtil.getMapFromXML(buffer.toString());
        PushNotificationHandler handler = PushNotificationHandler.getHandler(msg);
        String replyMsg = handler.handleNotification(msg);

        logger.info("====== start reply message ======");
        logger.info("\n" + replyMsg);
        logger.info("====== end reply message ======");

        resp.setCharacterEncoding("UTF-8");

        if (null == replyMsg) {
            resp.setContentType("text/html");
            resp.getWriter().write("success");
        } else {
            resp.setContentType("text/xml");
            resp.getWriter().write(replyMsg);
        }
    }

    /**
     * 返回404页面
     * @param req
     * @param rep
     * @return
     */
    @RequestMapping(value="404")
    public String to404(HttpServletRequest req, HttpServletResponse rep) {
        return "404";
    }

    /**
     * 将二进制转化为16进制字符串
     *
     * @param b
     *            二进制字节数组
     * @return String
     */
    private String byteToHexString(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                buffer.append("0");
            buffer.append(stmp);
        }

        return buffer.toString();
    }
}
