package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNum = request.getParameter("p");
System.out.println("!"+pageNum);
		if (pageNum == null) {
			pageNum = "1";
		}
		int limit = 5;

		System.out.println(pageNum+"!!");
		HashMap<String, Integer> pages = new HashMap<String, Integer>();
		int currentPage = Integer.parseInt(pageNum);

		int numPageGroup = (int) Math.ceil((double)currentPage/5);
		System.out.println("numPageGroup " + numPageGroup);

		int startPage = limit*(numPageGroup-1)+1;
		if (startPage < 1) {
			startPage = 1;
		}
		System.out.println("startage" + startPage);
		
		int totalPage = (new BoardRepository().countBoard()% limit) == 0? new BoardRepository().countBoard()/limit : new BoardRepository().countBoard()/limit+1;
		System.out.println("totalPage" + totalPage);
		
		int lastPage = startPage + (limit-1);
		if (lastPage > totalPage) {
			lastPage = totalPage;
		}
		
		pages.put("currentPage", currentPage);
		pages.put("numPageGroup", numPageGroup);
		pages.put("startPage", startPage);
		pages.put("totalPage", totalPage);
		pages.put("lastPage", lastPage);

		System.out.println("lastPage" + lastPage);
		System.out.println("currentPage" + currentPage);
		System.out.println("*****");
		List<BoardVo> vo = new BoardRepository().paging(currentPage);
		request.setAttribute("vo", vo);
		request.setAttribute("pages", pages);

		System.out.println(currentPage+"~~~~~~");
		for (BoardVo boardVo : vo) {
			boardVo.setRegDate(boardVo.getRegDate().substring(0, 16));
			boardVo.setDepth(boardVo.getDepth()*20);
		}

		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo != null) {
			session.setAttribute("authUser", userVo);
		}

		MvcUtils.forward("board/list", request, response);
	}

}
