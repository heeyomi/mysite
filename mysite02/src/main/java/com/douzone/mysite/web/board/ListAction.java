package com.douzone.mysite.web.board;

import java.io.IOException;
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
		if (pageNum == null) {
			pageNum = "1";
		}
		int limit = 5;

		HashMap<String, Integer> pages = new HashMap<String, Integer>();
		int currentPage = Integer.parseInt(pageNum);

		int startPage = limit*((int) Math.ceil((double)currentPage/5)-1)+1;
		if (startPage < 1) {
			startPage = 1;
		}
		
		int totalPage = (new BoardRepository().countBoard()% limit) == 0? new BoardRepository().countBoard()/limit : new BoardRepository().countBoard()/limit+1;
		
		int lastPage = startPage + (limit-1) > totalPage ? totalPage : startPage + (limit-1);
		
		int prevPage = currentPage-1 < 0 ? 1 : currentPage-1;
		
		int nextPage = currentPage + 1 > totalPage ? totalPage : currentPage+1;
		
		pages.put("currentPage", currentPage);
		pages.put("startPage", startPage);
		pages.put("totalPage", totalPage);
		pages.put("lastPage", lastPage);
		pages.put("prevPage", prevPage);
		pages.put("nextPage", nextPage);
		
		int totalBoard = new BoardRepository().countBoard();
		request.setAttribute("totalBoard", totalBoard);
		
		List<BoardVo> vo = new BoardRepository().paging(currentPage);
		
		request.setAttribute("vo", vo);
		request.setAttribute("pages", pages);

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
