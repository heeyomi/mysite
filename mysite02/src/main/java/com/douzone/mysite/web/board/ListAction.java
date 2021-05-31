package com.douzone.mysite.web.board;

import java.io.IOException;
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

		
		List<BoardVo> list = new BoardRepository().find();
		int totalPage = list.size() % 5 == 0 ? list.size()/5 : (list.size()/5 )+ 1;
		System.out.println(totalPage);
		int firstPage = 1; //시작 페이지
		int lastPage = 5; //끝 페이지
		
//		int nextPageNo = firstPageNo + lastPageNo;
//		int prevPageNo;
//		totalPage = 
//		firstPageNo = 1;
//		lastPageNo = 5;
//		nextPageNo = 6;
//		prevPageNo = 2;
// 		currentPageNo;
//		map = new.request..
//		map.put("lastPageNo", lastPageNo);
//		request.setAttribute("pageInfo", map);
		List<BoardVo> vo = new BoardRepository().find();
		request.setAttribute("vo", vo);
		
		for (BoardVo boardVo : vo) {
			boardVo.setRegDate(boardVo.getRegDate().substring(0, 16));
		}
		
		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo != null) {
			session.setAttribute("authUser", userVo);
		}
		
		MvcUtils.forward("board/list", request, response);
	}

}
