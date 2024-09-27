package model.like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;

public class LikeDAO { // 좋아요
	// SQL 삽입 명령어
	private final String INSERT = "INSERT INTO GOODLIKE VALUES(NVL((SELECT MAX(LIKE_NUM) FROM GOODLIKE), 0) + 1, ?,?)";
	// SQL 삭제 명령어
	private final String DELETE = "DELETE FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";
	// SQL 출력 명령어
	private final String SELECTONE= "SELECT LIKE_BOARD_NUM, LIKE_MEMBER_ID FROM GOODLIKE WHERE LIKE_BOARD_NUM = ? AND LIKE_MEMBER_ID = ?";

	public boolean insert(LikeDTO likeDTO){   // 입력
		System.out.println("goodLike.GoodLikeDAO.insert 시작");
		// 좋아요 추가
		// 좋아요 받은 글에 좋아요 개수 +1씩 입력
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;   // SQL 실행
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(INSERT);//INSERT 실행
			pstmt.setInt(1,likeDTO.getLike_board_num()); // 게시글
			pstmt.setString(2,likeDTO.getLike_member_id());   // 회원

			int result=pstmt.executeUpdate();
			if(result<=0) {
				System.err.println("goodLike.GoodLikeDAO.insert 실패");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("goodLike.GoodLikeDAO.insert SQL 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(conn,pstmt); // 종료
		}
		System.out.println("goodLike.GoodLikeDAO.insert 성공");
		return true;

	}
	private boolean update(LikeDTO likeDTO){   // 업데이트

		return false;
	}
	public boolean delete(LikeDTO likeDTO){   // 삭제
		System.out.println("goodLike.GoodLikeDAO.delete 시작");
		// 좋아요단 게시글에 좋아요 지우기.
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;   // SQL 실행
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(DELETE);
			pstmt.setInt(1,likeDTO.getLike_board_num());
			pstmt.setString(2,likeDTO.getLike_member_id());

			int result=pstmt.executeUpdate();
			if(result<=0) {
				System.err.println("goodLike.GoodLikeDAO.delete 실패");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("goodLike.GoodLikeDAO.delete SQL 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(conn,pstmt); // 종료
		}
		System.err.println("goodLike.GoodLikeDAO.delete 성공");
		return true;
	}
	private ArrayList<LikeDTO> selectAll(LikeDTO likeDTO){ // 전체 출력
		ArrayList<LikeDTO> datas=new ArrayList<LikeDTO>();

		return datas;
	}
	public LikeDTO selectOne(LikeDTO likeDTO){// 한명 출력
		System.out.println("goodLike.GoodLikeDAO.selectOne 시작");
		LikeDTO data=null;
		// - 좋아요를 했는지 안했는지 여부 확인
		// - 로그인을 하고 나갔다가 들어왔을 때, 좋아요가 남아있어야 함
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;   // SQL 실행
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(SELECTONE);
			pstmt.setInt(1, likeDTO.getLike_board_num());
			pstmt.setString(2, likeDTO.getLike_member_id());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				data = new LikeDTO();
				data.setLike_board_num(rs.getInt("like_board_num"));
				data.setLike_member_id(rs.getString("like_member_id"));

			}
		}
		catch (SQLException e) {
			System.err.println("goodLike.GoodLikeDAO.selectOne SQL 실패");
			return null;
		} finally {
			JDBCUtil.disconnect(conn,pstmt); // 종료
			System.out.println("goodLike.GoodLikeDAO.selectOne 성공");
		}
		return data;
	}
}
