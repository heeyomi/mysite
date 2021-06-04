package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;
	
	public boolean insert(GuestbookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = dataSource.getConnection();
			String sql = "insert into guestbook values(null, ?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			pstmt.setString(4, vo.getRegDate());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (Exception e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new GuestbookRepositoryException(e.getMessage());
			}
		}
		return result;
	}


	public List<GuestbookVo> find() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public boolean delete(Long no, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = dataSource.getConnection();
			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.setString(2, pw);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new GuestbookRepositoryException(e.getMessage());
			}
		}
		return result;

	}

	public GuestbookVo findByNo(long no) {
		GuestbookVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			String sql = "select * from guestbook where no = ? order by reg_date desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long n = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String regDate = rs.getString(5);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(n);
				vo.setName(name);
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setRegDate(regDate);

				result = vo;
			}

		} catch (Exception e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			try {
				if (rs!=null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new GuestbookRepositoryException(e.getMessage());
			}
		}

		return result;
	}



}
