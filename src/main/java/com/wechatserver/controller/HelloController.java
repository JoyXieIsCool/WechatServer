package com.wechatserver.controller;

import com.wechatserver.handler.PushNotificationHandler;
import com.wechatserver.util.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class HelloController {

	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model, String signature, String timestamp, String nonce, String echostr) {
		logger.info("~~~~~~~~ yo yo yo ~~~~~~~~~~");
		System.out.println("in~~" + timestamp);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		String result = formatter.format(new Date());
		model.addAttribute("message", result);
		return "hello";
	}

	@RequestMapping(method = RequestMethod.POST)
	public void getXML(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		resp.setContentType("text/xml");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		writer.write( "<xml>\n" +
				"    <ToUserName><![CDATA[xyz]]></ToUserName>\n" +
				"    <FromUserName><![CDATA[abc]]></FromUserName>\n" +
				"    <CreateTime>12345678</CreateTime>\n" +
				"    <MsgType><![CDATA[text]]></MsgType>\n" +
				"    <Content><![CDATA[你好]]></Content>\n" +
				"</xml>");
	}

	@RequestMapping(value = "/txt")
	public void replyTextMsg(HttpServletResponse resp) {
		String xmlString = "<xml>\n" +
				"<ToUserName><![CDATA[%s]]></ToUserName>\n" +
				"<FromUserName><![CDATA[%s]]></FromUserName>\n" +
				"<CreateTime>%d</CreateTime>\n" +
				"<MsgType><![CDATA[text]]></MsgType>\n" +
				"<Content><![CDATA[你好]]></Content>\n" +
				"</xml>";
		try {
			xmlString = String.format(xmlString, "to91小站", "from Joey", System.currentTimeMillis());
			Map<String, Object> map = XMLUtil.getMapFromXML(xmlString);

			PushNotificationHandler handler = PushNotificationHandler.getHandler(map);
			String reply = handler.handleNotification(map);

			System.out.println(map);
			System.out.println(reply);

			resp.setContentType("text/xml");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();
			writer.write(reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}