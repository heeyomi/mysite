package com.douzone.mysite.repository;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(GuestbookVo vo) {
		System.out.println(vo);
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println(vo);
		return count == 1;
	}

	public List<GuestbookVo> find() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public boolean delete(GuestbookVo vo) {
		int count = sqlSession.delete("guestbook.delete", vo);
		return count == 1;
	}

//	public GuestbookVo findByNo(long no) {
//		GuestbookVo result = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select * from guestbook where no = ? order by reg_date desc";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setLong(1, no);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				Long n = rs.getLong(1);
//				String name = rs.getString(2);
//				String password = rs.getString(3);
//				String message = rs.getString(4);
//				String regDate = rs.getString(5);
//
//				GuestbookVo vo = new GuestbookVo();
//				vo.setNo(n);
//				vo.setName(name);
//				vo.setPassword(password);
//				vo.setMessage(message);
//				vo.setRegDate(regDate);
//
//				result = vo;
//			}
//
//		} catch (Exception e) {
//			throw new GuestbookRepositoryException(e.getMessage());
//		} finally {
//			try {
//				if (rs!=null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (Exception e) {
//				throw new GuestbookRepositoryException(e.getMessage());
//			}
//		}
//
//		return result;
//	}



}
