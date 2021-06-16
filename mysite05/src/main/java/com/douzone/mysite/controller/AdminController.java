package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.StieService;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private StieService adminService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo siteVo = adminService.find();
		System.out.println("#############################3");
		System.out.println(siteVo.toString());
		System.out.println("#############################3");
		model.addAttribute("siteVo",siteVo);
		return "admin/main";
	}

	@RequestMapping(value="/main/update", method = RequestMethod.POST)
	public String updateMain(@RequestParam(value="file") MultipartFile file , SiteVo vo, Model model) {
		String url = fileUploadService.resotre(file);
		vo.setProfileURL(url);
		adminService.updateMain(vo);
		return "redirect:/admin";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		
		return "admin/guestbook";
	}
	@RequestMapping("/board")
	public String board() {
		
		return "admin/board";
	}
	@RequestMapping("/user")
	public String user() {
		
		return "admin/user";
	}
	
	
}
