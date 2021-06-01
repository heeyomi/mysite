package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;


public class BoardRepository {
	public boolean insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
			String sql = "insert into board values(null, ?, ?, ?, ?, ifnull((select max(group_no)+1 from board as b), 1), ?, ?, ?);";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getRegDate());
			pstmt.setInt(4, vo.getHit());
			pstmt.setInt(5, vo.getOrderNo());
			pstmt.setInt(6, vo.getDepth());
			pstmt.setLong(7, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (Exception e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3307/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}
		return conn;
	}

	public List<BoardVo> find() {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select a.no, a.title, a.contents, a.reg_date, a.hit, a.group_no, a.order_no, a.depth, a.user_no, b.name from board a, user b where a.user_no = b.no order by group_no desc, order_no asc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				String regDate = rs.getString(4);
				int hit = rs.getInt(5);
				int groupNo = rs.getInt(6);
				int orderNO = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(content);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNO);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);

				result.add(vo);
			}

		} catch (Exception e) {
			System.out.println("error:"+e);
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
				e.printStackTrace();
			}
		}

		return result;
	}

	public BoardVo findByNo(Long boardNo) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select * from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				String regDate = rs.getString(4);
				int hit = rs.getInt(5);
				int groupNo = rs.getInt(6);
				int orderNO = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(content);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNO);
				vo.setDepth(depth);
				vo.setUserNo(userNo);

				result = vo;
			}

		} catch (Exception e) {
			System.out.println("error:"+e);
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
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean updateBoard(String newTitle, String newContent, Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
			String sql = "update board set title =?, contents=? where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newTitle);
			pstmt.setString(2, newContent);
			pstmt.setLong(3, no);

			int count = pstmt.executeUpdate();
			result = count == 1;


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean updateHit(Long no, int newhit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
			String sql = "update board set hit = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newhit);
			pstmt.setLong(2, no);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean insertReply(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
			String sql = "insert into board values(null, ?, ?, ?, ?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getRegDate());
			pstmt.setInt(4, vo.getHit());
			pstmt.setInt(5, vo.getGroupNo());
			pstmt.setInt(6, vo.getOrderNo());
			pstmt.setInt(7, vo.getDepth());
			pstmt.setLong(8, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (Exception e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;		
	}

	public boolean updateReply(int groupNo, int orderNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
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
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs!= null) {
					rs.close();
				}
				if (pstmt!=null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<BoardVo> paging(int firstPage) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select a.no, a.title, a.contents, a.reg_date, a.hit, a.group_no, a.order_no, a.depth, a.user_no, b.name from board a, user b where a.user_no = b.no order by group_no desc, order_no asc limit ?, 5";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, firstPage);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				String regDate = rs.getString(4);
				int hit = rs.getInt(5);
				int groupNo = rs.getInt(6);
				int orderNO = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(content);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNO);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);

				result.add(vo);
			}

		} catch (Exception e) {
			System.out.println("error:"+e);
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
				e.printStackTrace();
			}
		}

		return result;
	}

}
