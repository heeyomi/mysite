package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public List<BoardVo> getBoardList() {
		List<BoardVo> list = boardRepository.find();
		for (BoardVo boardVo : list) {
			boardVo.setRegDate(boardVo.getRegDate().substring(0, 19));
		}
		return list;
	}

	public BoardVo findByNo(long no) {
		return boardRepository.findByNo(no);
	}

	public int update(BoardVo vo) {
		return boardRepository.updateBoard(vo);
	}

	public int write(BoardVo vo) {
		return boardRepository.insert(vo);
	}

	public int findMaxGroupNo() {
		return boardRepository.findMaxGroupNo();
	}

	public int updateHit(BoardVo vo) {
		return boardRepository.updateHit(vo);

	}

	public List<BoardVo> paging(long page) {
		return boardRepository.paging(page);
	}


	public Map<String, Integer> pages(String page) {
		int limit = 5;

		int currentPage = Integer.parseInt(page);
		int totalPage = (boardRepository.countBoard()% limit) == 0? boardRepository.countBoard()/limit : boardRepository.countBoard()/limit+1;

		int startPage = limit * ((int) Math.ceil((double)currentPage/limit)-1)+1;
		if (startPage < 1) {
			startPage = 1;
		}
		int prevPage = currentPage-1 < 0 ? 1 : currentPage-1;
		int lastPage = startPage + (limit-1) > totalPage ? totalPage : startPage + (limit-1);
		int nextPage = currentPage + 1 > totalPage ? totalPage : currentPage+1;
		
		int totalBoard =boardRepository.countBoard();
		HashMap<String, Integer> pages = new HashMap<String, Integer>();
		pages.put("currentPage", currentPage);
		pages.put("startPage", startPage);
		pages.put("totalPage", totalPage);
		pages.put("lastPage", lastPage);
		pages.put("prevPage", prevPage);
		pages.put("nextPage", nextPage);
		
		return pages;
	}
}
