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
import org.springframework.web.bind.annotation.RequestParam;

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
	public String index(@RequestParam(value = "p", required = true, defaultValue = "1" ) int page,  Model model) {
		List<BoardVo> list = boardService.paging(page); 
		model.addAttribute("list",list);
		Map<String, Integer> pages = boardService.pages(page);
		model.addAttribute("pages",pages);
		return "board/index";
	}

	@Auth
	@RequestMapping(value="/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@Auth
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo vo) {
		boardService.write(authUser, vo);
		return "redirect:/board";
	}

	@RequestMapping("/view/{no}")
	public String view(@PathVariable(value="no") String no, Model model, @AuthUser UserVo authUser) {
		BoardVo vo = boardService.findByNo(Long.parseLong(no));
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
	public String modify(@PathVariable(value="no") String no, BoardVo vo, Model model) {
		boardService.update(vo, no);
		return "redirect:/board";
	}

	@Auth
	@RequestMapping(value="/reply/{no}", method = RequestMethod.GET)
	public String reply(@PathVariable(value="no") String no, Model model) {
		model.addAttribute("boardVo", boardService.findByNo(Long.parseLong(no)));
		return "board/reply";
	}

	@Auth
	@RequestMapping(value="/reply/{no}", method = RequestMethod.POST)
	public String reply(@PathVariable(value="no") String no, @AuthUser UserVo authUser, HttpServletRequest request, BoardVo vo) {
		boardService.insertReply(authUser, vo, no);
		return "redirect:/board/view";
	}

	@Auth
	@RequestMapping(value="/delete/{no}")
	public String delete(@PathVariable(value="no") String no, @AuthUser UserVo authUser) {
		BoardVo vo = boardService.findByNo(Long.parseLong(no));
		if (vo.getUserNo() == authUser.getNo()) {
			boardService.delete(Long.parseLong(no));
		}
		return "redirect:/board/view";
	}
}
