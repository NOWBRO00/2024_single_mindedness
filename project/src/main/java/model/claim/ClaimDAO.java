package model.claim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;

public class ClaimDAO { // 신고
	// 게시글 신고
	private final String INSERT_CLAIM_BOARD="INSERT INTO CLAIM (CLAIM_NUM,CLAIM_CATEGORY,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_TARGET_MEMBER_ID,CLAIM_STATUS,CLAIM_REPORTER_ID) \r\n"
			+ "VALUES (NVL((SELECT MAX(CLAIM_NUM) FROM CLAIM),0)+1,?,?,null,?,'T',?)";
	// 댓글 신고
	private final String INSERT_CLAIM_REPLY="INSERT INTO CLAIM (CLAIM_NUM,CLAIM_CATEGORY,CLAIM_BOARD_NUM,CLAIM_REPLY_NUM,CLAIM_TARGET_MEMBER_ID,CLAIM_STATUS,CLAIM_REPORTER_ID) \r\n"
			+ "VALUES (NVL((SELECT MAX(CLAIM_NUM) FROM CLAIM),0)+1,?,null,?,?,'T',?)";
	// 신고여부 확인
	private final String CLAIM_SELECT_ONE="SELECT CLAIM_NUM FROM CLAIM WHERE CLAIM_NUM=?";

	public boolean insert(ClaimDTO claimDTO){	// 입력
		// 신고 등록 게시글, 댓글
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;	// SQL문 쿼리 실행 객체 선언
		try {
			// 게시글 신고 
			System.out.println("claim.ClaimDAO.insert 시작");
			if(claimDTO.getClaim_condition().equals("INSERT_CLAIM_BOARD")) { 
				// 컨디션이 INSERT_CLAIM_BOARD 라면
				//INSERT : 카테고리,게시글 번호(FK), 신고당한 유저, 신고자
				System.out.println("claim.ClaimDAO.INSERT_CLAIM_BOARD 시작");
				pstmt=conn.prepareStatement(INSERT_CLAIM_BOARD); //INSERT 실행
				pstmt.setString(1, claimDTO.getClaim_category()); // 카테고리 ('욕설','성희롱','부적절컨텐츠')
				pstmt.setInt(2, claimDTO.getClaim_board_num()); // 게시글 번호 - FK
				pstmt.setString(3, claimDTO.getClaim_target_member_id()); // 신고 당한 유저
				pstmt.setString(4, claimDTO.getClaim_reporter_id()); 	// 신고한 유저
			}
			// 댓글 신고
			else if(claimDTO.getClaim_condition().equals("INSERT_CLAIM_REPLY")) {
				// 컨디션이 INSERT_CLAIM_REPLY 라면
				//INSERT : 카테고리,댓글 번호(FK), 신고당한 유저, 신고자
				System.out.println("claim.ClaimDAO.INSERT_CLAIM_REPLY 시작");
				pstmt=conn.prepareStatement(INSERT_CLAIM_REPLY); //INSERT 실행
				pstmt.setString(1, claimDTO.getClaim_category()); // 카테고리 ('욕설','성희롱','부적절컨텐츠')
				pstmt.setInt(2, claimDTO.getClaim_reply_num()); // 댓글 번호 - FK
				pstmt.setString(3, claimDTO.getClaim_target_member_id()); // 신고 당한 유저
				pstmt.setString(4, claimDTO.getClaim_reporter_id()); // 신고한 유저
			}

			int result=pstmt.executeUpdate();
			System.out.println("claim.ClaimDAO.insert result:["+result+"]");
			if(result<=0) {
				// 만약 반환 된 수가 0 이하면 변경된 행이 없음으로 
				System.err.println("claim.ClaimDAO.insert 실패");
				// false 반환
				return false;
			}
			System.out.println("claim.ClaimDAO.insert 성공");
		}
		catch (SQLException e) {
			// sql문 실패여도 
			System.err.println("claim.ClaimDAO.insert SQL문 실패");
			// false 반환
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt); // 연결 해제/종료

		return true;
	}
	private boolean update(ClaimDTO claimDTO){	// 업데이트
		// 신고 내역 댓글, 게시글 (트리거 사용)

		return false;
	}private boolean delete(ClaimDTO claimDTO){	// 지우기
		// 신고 내역 지우기(관리자)

		return false;
	}
	private ArrayList<ClaimDTO> selectAll(ClaimDTO claimDTO){ // 전체 출력
		ArrayList<ClaimDTO> datas=new ArrayList<ClaimDTO>();
		//신고 내역 전체 출력(관리자)
		//신고 내역 중 카테고리 별 출력
		return datas;
	}
	public ClaimDTO selectOne(ClaimDTO claimDTO){// 한명 출력
		// 신고 여부 확인
		ClaimDTO data=null;
		
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;	// SQL 실행
		try {
			//신고자 확인
			System.out.println("claim.ClaimDAO.selectOne 시작");
			pstmt=conn.prepareStatement(CLAIM_SELECT_ONE);
			// 신고 번호 받아서
			pstmt.setInt(1, claimDTO.getClaim_num());
			
			// R이기 때문에 ResultSet 객체가 반환되고, executeQuery 사용한다.
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				data=new ClaimDTO();
				System.out.println("claim.ClaimDAO.selectOne 쿼리 읽기 시작");
				data.setClaim_num(rs.getInt("CLAIM_NUM"));
			}
			else {
				// 번호가 없을경우 null값 반환
				return null;
			}
			System.out.println("claim.ClaimDAO.selectOne data:"+data);
		}
		catch (SQLException e) {
			System.err.println("SQL문 실패");
		}
		// [4] 단계 연결해제
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ClaimDAO.selectOne 종료");
				
		return data;
	}
}
