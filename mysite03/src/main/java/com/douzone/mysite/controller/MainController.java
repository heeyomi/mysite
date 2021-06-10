package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.StieService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private ServletContext application;
	
	@Autowired
	private StieService siteService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.find();
		model.addAttribute("siteVo", vo);
		application.setAttribute("title", vo.getTitle());
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg1")
	public String message() {
		return "하위~";
	}
	
	@ResponseBody
	@RequestMapping("/msg2")
	public UserVo message2() {
		UserVo vo = new UserVo();
		vo.setNo(10L);
		vo.setEmail("heeyomi@gmail.com");
		vo.setName("윤희경");
		return vo;
	}
}
