package com.wechatserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}