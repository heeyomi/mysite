package com.douzone.mysite.web.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String newPW = request.getParameter("newPW");
		
		System.out.println(name);
		System.out.println(password);
		System.out.println(newPW);
		
		UserVo vo = new UserRepository().findByEmailAndPassword(email, password);
		if (vo == null) {
			request.setAttribute("result","fail");
			request.setAttribute("email",email);
			MvcUtils.forward("user/loginform", request, response);
			return;
		}
		
		vo.setNo(vo.getNo());
		vo.setName(name);
		vo.setEmail(vo.getEmail());
		vo.setName(vo.getName());
		vo.setPassword(newPW);
		vo.setGender(vo.getGender());
		
		new UserRepository().update(name, newPW, vo.getNo());
		// 인증처리(session 처리)
		HttpSession session =  request.getSession(true);
		session.setAttribute("authUser", vo);
		
		
		// main으로 리다이렉트
		MvcUtils.redirect(request.getContextPath(), request, response);
	}

}
