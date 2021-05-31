package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		totalPage = 
//		firstPageNo = 1;
//		lastPageNo = 10;
//		nextPageNo = 11;
//		prevPageNo = 2;
// 		currentPageNo;
//		map = new.request..
//		map.put("lastPageNo", lastPageNo);
//		request.setAttribute("pageInfo", map);

		MvcUtils.forward("board/list", request, response);
	}

}
