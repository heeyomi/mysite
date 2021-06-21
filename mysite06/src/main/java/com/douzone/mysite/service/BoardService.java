package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public BoardVo findByNo(long no) {
		return boardRepository.findByNo(no);
	}

	public int update(BoardVo vo, String no) {
		BoardVo origin = boardRepository.findByNo(Long.parseLong(no));
		origin.setContents(vo.getContents());
		origin.setTitle(vo.getTitle());
		return boardRepository.updateBoard(origin);
	}

	public int write(UserVo authUser, BoardVo vo) {
		BoardVo newBoard = new BoardVo();
		newBoard.setUserNo(authUser.getNo());
		newBoard.setTitle(vo.getTitle());
		newBoard.setContents(vo.getContents());
		newBoard.setDepth(0);
		newBoard.setHit(0);
		newBoard.setOrderNo(1);
		newBoard.setGroupNo(boardRepository.findMaxGroupNo()+1);
		newBoard.setUserName(authUser.getName());
		
		return boardRepository.insert(newBoard);
	}

	public int updateHit(BoardVo vo) {
		return boardRepository.updateHit(vo);

	}
	
	public int insertReply(UserVo authUser, BoardVo vo, String no) {
		
		BoardVo reply = new BoardVo();
		reply.setTitle(vo.getTitle());
		reply.setContents(vo.getContents());
		reply.setUserNo(authUser.getNo());
		reply.setUserName(authUser.getName());
		reply.setHit(0);
		
		BoardVo parent = boardRepository.findByNo(Long.parseLong(no));
		reply.setDepth(parent.getDepth()+1);
		reply.setGroupNo(parent.getGroupNo());
		reply.setOrderNo(parent.getOrderNo()+1);
		boardRepository.updateNo(reply);
		
		return boardRepository.insertReply(reply);
	}

	public Map<String, Object> getContentsList(int page, String keyword) {
		int limit = 5;

		int currentPage = page;
		int totalPage = (boardRepository.countBoard()% limit) == 0? boardRepository.countBoard()/limit : boardRepository.countBoard()/limit+1;

		int startPage = limit * ((int) Math.ceil((double)currentPage/limit)-1)+1;
		if (startPage < 1) {
			startPage = 1;
		}
		int prevPage = currentPage-1 < 0 ? 1 : currentPage-1;
		int lastPage = startPage + (limit-1) > totalPage ? totalPage : startPage + (limit-1);
		int nextPage = currentPage + 1 > totalPage ? totalPage : currentPage+1;
		int totalBoard = boardRepository.countBoard();
		List<BoardVo> list = boardRepository.paging((page-1) * 5, keyword);
		for (BoardVo boardVo : list) {
			boardVo.setRegDate(boardVo.getRegDate().substring(0, 19));
		}
		
		HashMap<String, Object> pages = new HashMap<String, Object>();
		pages.put("limit", limit);
		pages.put("currentPage", currentPage);
		pages.put("startPage", startPage);
		pages.put("totalPage", totalPage);
		pages.put("lastPage", lastPage);
		pages.put("prevPage", prevPage);
		pages.put("nextPage", nextPage);
		pages.put("totalBoard", totalBoard);
		pages.put("keyword", keyword);
		pages.put("list", list);
		
		return pages;
	}

	public int updateNo(BoardVo vo) {
		return boardRepository.updateNo(vo);
	}

	public int delete(Long no) {
		return boardRepository.delete(no);
		
	}

	public List<BoardVo> findByKwd(String kwd) {
		return boardRepository.findByKwd(kwd);
		
	}
}
