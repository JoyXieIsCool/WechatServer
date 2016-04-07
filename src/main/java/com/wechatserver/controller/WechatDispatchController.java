package com.wechatserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 做微信接入验证的Controller
 */
@Controller
@RequestMapping("/")
public class WechatDispatchController {

    private static final String TOKEN = "JoeyXieIsCool";
    @RequestMapping(value="verify", method = RequestMethod.GET)
    @ResponseBody
    public String verify(String signature, String timestamp, String nonce, String echostr) {
        System.out.println("signature:" + signature);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:    " +nonce);
        System.out.println("echostr:  " + echostr);

        String[] tempArr = new String[]{timestamp, nonce, TOKEN};
        Arrays.sort(tempArr);
        String conbineString = tempArr[0] + tempArr[1] + tempArr[2];
        try {
            byte[] digest = MessageDigest.getInstance("SHA-1").digest(conbineString.getBytes("utf-8"));
            String myEncryptResult = byte2hex(digest);
            System.out.println("myEncryptResult:" + myEncryptResult);
            if(myEncryptResult.equals(signature))
                return echostr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "false";
    }

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
    private String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }
}
