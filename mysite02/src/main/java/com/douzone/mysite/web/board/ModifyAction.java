package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo != null) {
			session.setAttribute("authUser", userVo);
		}
		
		String newTitle = request.getParameter("title");
		String newContents = request.getParameter("content");
		Long no = Long.parseLong(request.getParameter("no"));
		
		new BoardRepository().updateBoard(newTitle, newContents, no);
		System.out.println(no);
		
		MvcUtils.redirect(request.getContextPath()+"/board", request, response);
	}

}
