package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;

	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public List<BoardVo> paging(long page, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("keyword", keyword);
		return sqlSession.selectList("board.findAllByPageAndKeyword", map);
	}

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}

	public int updateBoard(BoardVo vo) {
		return sqlSession.update("board.updateBoard", vo);
	}

	public int updateHit(BoardVo vo) {
		return sqlSession.update("board.updateHit", vo);
	}

	public int insertReply(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);		
	}


	public int delete(Long no) {
		return sqlSession.delete("board.delete", no);
	}

	public int updateNo(BoardVo vo) {
		return sqlSession.update("board.updateNo", vo);
	}

	public int countBoard() {
		return sqlSession.selectOne("board.countBoard");
	}

	public int findMaxGroupNo() {
		return sqlSession.selectOne("board.findMaxGroupNo");
	}

	public List<BoardVo> findByKwd(String kwd) {
		Map<String, String> map = new HashMap<>();
		map.put("title", kwd);
		map.put("contents", kwd);
		map.put("name", kwd);
		
		return sqlSession.selectList("board.findByKwd", map);
	}



}
