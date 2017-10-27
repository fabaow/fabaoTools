package com.fabao.controller;

import com.fabao.service.TestService;
import com.fabao.task.TestTask;
import com.fabao.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello {
	@Autowired
	TestService t;
	@Autowired
	TestTask tt;
	@RequestMapping("/hello")
	private String hello(Model model){
		model.addAttribute("people", t.getName());
		return "hello";
	}
	@RequestMapping("/hello1")
	private String hello1(Model model){
		String message=FileUtil.getTextFromFile("c:/tmp/", "dddd.txt");
		model.addAttribute("message",message);
		return "hello";
	}

}
