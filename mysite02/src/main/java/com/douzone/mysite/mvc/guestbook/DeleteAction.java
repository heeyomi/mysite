package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MvcUtils;
import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");
		
		List<GuestbookVo> list = new GuestbookRepository().find();
		
		for (GuestbookVo vo : list) {
			if (no == vo.getNo() && password.equals(vo.getPassword())) {
				new GuestbookRepository().delete(no, password);
				break;
			}
		}
		MvcUtils.redirect(request.getContextPath()+"/guestbook", request, response);
	}
}
