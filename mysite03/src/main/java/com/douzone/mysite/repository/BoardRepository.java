package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		System.out.println(vo.toString());
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

	public boolean delete(Long no) {
		boolean result = false;
		String sql = "delete from board where no = ?";
		return result;
	}


	public int updateHit(BoardVo vo) {
		return sqlSession.update("board.updateHit", vo);
	}

	public boolean insertReply(BoardVo vo) {
		boolean result = false;

		String sql = "insert into board values(null, ?, ?, ?, ?, ?, ?, ?, ?);";
		return result;		
	}

	public boolean updateReply(int groupNo, int orderNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			String sql = "update board set order_no = (order_no+1)  where group_no = ? and order_no >=? ";
			pstmt = conn.prepareStatement(sql);


			pstmt.setInt(1, groupNo);
			pstmt.setInt(2, orderNo);

			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int countBoard() {
		return sqlSession.selectOne("board.countBoard");
	}

	public int findMaxGroupNo() {
		return sqlSession.selectOne("board.findMaxGroupNo");
	}



}
