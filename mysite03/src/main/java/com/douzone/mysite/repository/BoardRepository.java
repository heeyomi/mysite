package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> find() {
		return sqlSession.selectList("board.findAll");
	}

	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public List<BoardVo> paging(long page) {
		return sqlSession.selectList("board.paging", (page-1)* 5);
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



}
