package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MvcUtils;
import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		Timestamp regDate = Timestamp.valueOf(LocalDateTime.now());
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(content);
		vo.setRegDate(regDate);
		
		new GuestbookRepository().insert(vo);
		MvcUtils.redirect(request.getContextPath()+"/guestbook", request, response);
		
	}
}
