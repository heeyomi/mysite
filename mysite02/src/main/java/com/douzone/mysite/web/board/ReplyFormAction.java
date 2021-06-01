package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo == null) {
			MvcUtils.redirect(request.getContextPath()+"/board", request, response);
			return;
		}
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo boardVo = new BoardRepository().findByNo(no);
		request.setAttribute("boardVo", boardVo);
		
		
		MvcUtils.forward("board/reply", request, response);
	}

}
