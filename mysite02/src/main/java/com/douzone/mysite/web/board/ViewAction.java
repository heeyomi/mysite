package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo vo = new BoardRepository().findByNo(Long.parseLong(request.getParameter("no")));
		new BoardRepository().updateHit(vo.getNo(), vo.getHit()+1);
		request.setAttribute("vo", vo);
		MvcUtils.forward("board/view", request, response);
	}

}
