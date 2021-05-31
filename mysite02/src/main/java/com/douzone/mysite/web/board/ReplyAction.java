package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo == null) {
			return;
		}
		
		System.out.println("reply");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		System.out.println(title);
		System.out.println(content);
		
		MvcUtils.redirect(request.getContextPath()+"/board", request, response);
		
	}

}
