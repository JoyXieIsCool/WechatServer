package com.wechatserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/test.do")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String result = formatter.format(new Date());
		model.addAttribute("message", "Current Time: " + result);
		return "hello";
	}
}