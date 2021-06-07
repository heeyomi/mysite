package com.douzone.mysite.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping({"","/{page}"})
	public String index(@PathVariable(value = "p", required = false) String page,  Model model) {
		if (page == null) {
			page = "1";
		}
		System.out.println(page);
		List<BoardVo> list = boardService.paging(Long.parseLong(page)); 
		model.addAttribute("list",list);
		Map<String, Integer> pages = boardService.pages(page);
		model.addAttribute("pages",pages);
		return "board/index";
	}
	
	@RequestMapping(value="/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@Auth
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, String title, String content) {
		BoardVo vo = new BoardVo();
		vo.setUserNo(authUser.getNo());
		vo.setTitle(title);
		vo.setContents(content);
		vo.setDepth(0);
		vo.setHit(0);
		vo.setOrderNo(0);
		vo.setGroupNo(boardService.findMaxGroupNo()+1);
		vo.setUserName(authUser.getName());
		boardService.write(vo);
		return "redirect:/board";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable(value="no") String no, Model model, @AuthUser UserVo authUser) {
		BoardVo vo = boardService.findByNo(Long.parseLong(no));
		System.out.println(vo.toString());
		model.addAttribute("vo", vo);
		model.addAttribute("authUser", authUser);
		boardService.updateHit(vo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="modify/{no}", method = RequestMethod.GET)
	public String modify(@PathVariable(value="no") String no, Model model, @AuthUser UserVo authUser) {
		BoardVo vo = boardService.findByNo(Long.parseLong(no));
		if (vo.getUserNo() != authUser.getNo()) {
			return "redirect:/board";
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("authUser", authUser);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="modify/{no}", method = RequestMethod.POST)
	public String modify(@PathVariable(value="no") String no, HttpServletRequest request, @AuthUser UserVo authUser, Model model) {
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		model.addAttribute("authUser", authUser);
	
		BoardVo vo = boardService.findByNo(Long.parseLong(no));
		if (vo.getUserNo() != authUser.getNo()) {
			return "redirect:/board";
		}
		
		vo.setTitle(title);
		vo.setContents(contents);
		
		boardService.update(vo);
		return "redirect:/board";
	}
}
