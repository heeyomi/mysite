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

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		int orderNo = Integer.parseInt(request.getParameter("orderNo"));
		int depth = Integer.parseInt(request.getParameter("depth"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(); 
		
		new BoardRepository().updateReply(groupNo, orderNo+1);
		
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContents(content);
		boardVo.setRegDate(sdf.format(date));
		boardVo.setHit(0);
		boardVo.setGroupNo(groupNo);
		boardVo.setOrderNo(orderNo+1);
		boardVo.setDepth(depth+1);
		boardVo.setUserNo(userVo.getNo());
		
		new BoardRepository().insertReply(boardVo);
		
		MvcUtils.redirect(request.getContextPath()+"/board", request, response);
		
	}

}
