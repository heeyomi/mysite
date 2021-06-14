package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;

	@Autowired
	private FileUploadService fileuUploadService;

	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> vo = galleryService.findAll();
		model.addAttribute("vo", vo);
		return "gallery/index";
	}

	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public String upload(@RequestParam(value="file") MultipartFile file, @RequestParam(value="comments") String comments) {
		String url = fileuUploadService.resotre(file);
		GalleryVo vo = new GalleryVo();
		vo.setComment(comments);
		vo.setUrl(url);
		galleryService.upload(vo);
		return "redirect:/gallery";
	}

	@Auth(role="ADMIN")
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable(value="no") String no, @AuthUser UserVo authUser) {
		galleryService.delete(Long.parseLong(no));
		return "redirect:/gallery";
	}

}
