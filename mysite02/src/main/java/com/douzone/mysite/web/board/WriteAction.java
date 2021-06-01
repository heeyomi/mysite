package com.douzone.mysite.web.board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session =  request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo == null) {
			return;
		}
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(); 
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setHit(0);
		vo.setGroupNo(1);
		vo.setOrderNo(1);
		vo.setDepth(0);
		vo.setUserNo(userVo.getNo());
		vo.setUserName(userVo.getName());
		vo.setRegDate(sdf.format(date));
		
		new BoardRepository().insert(vo);
		
		MvcUtils.redirect(request.getContextPath()+"/board", request, response);
	}

}
