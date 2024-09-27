package model.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;



public class ReplyDAO {	//댓글

	private final String REPLY_INSERT="INSERT INTO REPLY (REPLY_NUM,REPLY_CONTENT,REPLY_WRITER_ID,REPLY_PRIVATE,REPLY_BOARD_NUM) \r\n"
			+ "VALUES (COALESCE((SELECT MAX(REPLY_NUM) FROM REPLY),0)+1,?,?,?,?)";
	private final String REPLY_UPDATE="UPDATE REPLY SET REPLY_CONTENT=?, REPLY_PRIVATE=? WHERE REPLY_NUM=?";
	private final String REPLY_DELETE="DELETE FROM REPLY WHERE REPLY_NUM=?";
	private final String REPLY_ALL="SELECT R.REPLY_NUM,R.REPLY_CONTENT,R.REPLY_WRITER_ID, M.MEMBER_NICKNAME ,COALESCE(ML.LV_COUNT,1) AS MEMBER_LEVEL,R.REPLY_REGISTRATION_DATE,R.REPLY_PRIVATE, M.MEMBER_PROFILE\r\n"
			+ "FROM REPLY R\r\n"
			+ "JOIN MEMBER M ON R.REPLY_WRITER_ID = M.MEMBER_ID\r\n"
			+ "JOIN BOARD B ON R.REPLY_BOARD_NUM = B.BOARD_NUM\r\n"
			+ "LEFT JOIN (SELECT B.BOARD_WRITER_ID,COUNT(B.BOARD_WRITER_ID) AS LV_COUNT FROM BOARD B GROUP BY B.BOARD_WRITER_ID)ML ON R.REPLY_WRITER_ID=ML.BOARD_WRITER_ID\r\n"
			+ "WHERE B.BOARD_NUM=? ORDER BY REPLY_NUM DESC";


	public boolean insert(ReplyDTO replyDTO){	// 추가
		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// 댓글 작성
		// SQL 쿼리를 읽어올 준비를 한다.
		// 쿼리를 모두 읽어온 뒤 닫아줘야 하므로, try문 밖에 초기화 한다.
		PreparedStatement pstmt=null;
		try {
			System.out.println("model.ReplyDAO.insert 시작");
			// conn을 통하여 쿼리를 읽어온다.
			pstmt = conn.prepareStatement(REPLY_INSERT);
			// 원하는 내역을 DTO에서 가져와 저장해준다. (댓글 내용, 작성자, 비공개 여부, 해당 글 번호)
			pstmt.setString(1, replyDTO.getReply_content()); // 댓글 내용
			pstmt.setString(2, replyDTO.getReply_writer_id()); // 댓글 작성자

			// 비공개 여부 기존 boolean 타입 -> int 타입으로 변경됨. 24.09.03
			pstmt.setInt(3, replyDTO.getReply_private()); // 댓글 비공개 여부
			pstmt.setInt(4, replyDTO.getReply_board_num()); // 댓글을 단 글 번호

			// insert : executeUpdate - 변경이 된 행 수 정수로 반환됨
			int result = pstmt.executeUpdate();
			System.out.println("model.ReplyDAO.insert result : " + result);
			// 따라서 변경이 된 수가 0보다 작다면 오류이므로 false 반환해준다.
			if(result <= 0) { // 만약 행 변환이 된 수가(result가) 0보다 작거나 같다면
				System.out.println("model.ReplyDAO.insert 행 변경 실패");
				return false;
			}
			System.out.println("model.ReplyDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("SQL문 실패");
			// SQL문 실패시에도
			return false;
		}

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReplyDAO.insert 종료");

		return true;
	}

	public boolean update(ReplyDTO replyDTO){	// 업데이트(변경)
		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// 댓글 비공개 ↔ 공개 + 댓글내용 SET : 수정시 둘 다
		// PreparedStatement를 사용하여 쿼리를 읽어올 준비를 한다.
		PreparedStatement pstmt = null;

		try {
			System.out.println("model.ReplyDAO.update 시작");
			// conn을 이용하여 SQL 쿼리를 읽어온다.
			pstmt=conn.prepareStatement(REPLY_UPDATE);

			// 원하는 내역을 DTO에서 가져와 저장해준다. (댓글 내용 / 댓글 비공개여부 / 댓글 번호)
			pstmt.setString(1, replyDTO.getReply_content()); // 댓글 내용

			// 비공개 여부 기존 boolean 타입 -> int 타입으로 변경됨. 24.09.03
			pstmt.setInt(2, replyDTO.getReply_private()); // 댓글 비공개 여부

			pstmt.setInt(3, replyDTO.getReply_num()); // 글 번호
			// update : executeUpdate - 변경이 된 행 수 정수로 반환됨
			int result = pstmt.executeUpdate();
			System.out.println("model.ReplyDAO.update result : " + result);
			// 따라서 변경이 된 수가 0보다 작다면 오류이므로 false 반환해준다.
			if(result<=0) { // 변경이 된 수가 0보다 작다면
				System.out.println("model.ReplyDAO.update 행 변경 실패");
				return false;
			}
			System.out.println("model.ReplyDAO.update 행 변경 성공");

		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			return false;
		}


		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReplyDAO.update 종료");

		return true;
	}

	public boolean delete(ReplyDTO replyDTO){	// 지우기

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// 댓글 삭제
		// 쿼리를 읽어올 준비를 한다.
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.ReplyDAO.delete 시작");
			// conn을 사용하여 SQL 쿼리를 읽어온다.
			pstmt=conn.prepareStatement(REPLY_DELETE);
			// 1번 파라미터에 DTO에서 값을 가져와 저장한다. (삭제할 댓글 번호)
			pstmt.setInt(1, replyDTO.getReply_num()); // 댓글 번호
			// delete : executeUpdate - 변경이 된 행 수 정수로 반환됨
			int result = pstmt.executeUpdate();
			System.out.println("model.ReplyDAO.delete result : " + result);
			// 따라서 변경이 된 수가 0보다 작다면 오류이므로 false 반환해준다.
			if(result <= 0) { // 변변경이 된 수가 0보다 작거나 같다면
				System.out.println("model.ReplyDAO.delete 행 변경 성공");
				return false;
			}
			System.out.println("model.ReplyDAO.delete 행 변경 실패");

		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패해도 false 반환
			return false;
		}


		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReplyDAO.delete 종료");

		return true;
	}

	public ArrayList<ReplyDTO> selectAll(ReplyDTO replyDTO){ // 전체 출력
		ArrayList<ReplyDTO> datas=new ArrayList<ReplyDTO>();

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// 댓글 삭제
		// PreparedStatement를 사용하여 쿼리를 읽어올 준비를 한 뒤
		PreparedStatement pstmt = null;

		try {
			System.out.println("model.ReplyDAO.selectAll 시작");
			// conn을 이용해 SQL 쿼리를 읽어온다.
			pstmt=conn.prepareStatement(REPLY_ALL);
			// 특정한 데이터를 찾기 위해, 1번 파라미터에 값을 저장해준다. (해당하는 글 번호)
			pstmt.setInt(1, replyDTO.getReply_board_num()); // 댓글을 단 글 번호
			// selectAll : executeQuery - ResultSet 객체 반환 / 쿼리를 읽어올 수 있게 함
			ResultSet rs = pstmt.executeQuery();
			// next 메서드를 이용하여 쿼리를 한 줄씩 읽어온다
			// 이때 여러개의 댓글을 가지고 와야 하므로 while 문으로 더이상 읽어올 글이 없을 때까지 반복한다
			while(rs.next()) {
				System.out.println("model.ReplyDAO.selectAll while문 시작(쿼리 한줄씩 읽어오기)");
				// C에게 반환해줘야 하므로 ResultSet에 있는 객체를 받아와 data에 저장해준다.
				// 여러개의 내용을 저장해서 보내줘야 하므로 DTO 객체 생성
				ReplyDTO data = new ReplyDTO();
				data.setReply_num(rs.getInt("REPLY_NUM"));
				data.setReply_content(rs.getString("REPLY_CONTENT")); // 댓글 내용
				data.setReply_writer_id(rs.getString("REPLY_WRITER_ID")); // 댓글 작성자 ID
				data.setReply_writer_nickname("MEMBER_NICKNAME");// 댓글 작성자(닉네임)
				data.setReply_writer_level(rs.getInt("MEMBER_LEVEL")); // 댓글 작성자의 lv

				// 날짜 타입 LocalDate → Date 타입으로 변경 24.09.03
				data.setReply_registration_date(rs.getDate("REPLY_REGISTRATION_DATE")); // 댓글 작성일

				// 댓글 비공개 여부 boolean → int 타입으로 변경 24.09.03
				data.setReply_private(rs.getInt("REPLY_PRIVATE")); // 댓글 비공개 여부
				
				data.setReply_member_profile(rs.getString("MEMBER_PROFILE")); // 회원의 프로필 이미지
 
				System.out.println("model.ReplyDAO.selectAll data : " + data);
				// AL에 저장 (반환하기 위해)
				datas.add(data);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL문 실패");
		}  

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReplyDAO.selectAll 종료");

		return datas;
	}


	// 사용하지 않는 기능으로 private 처리
	private ReplyDTO selectOne(ReplyDTO replyDTO){// 힌개 출력
		ReplyDTO data=null;

		return data;
	}
}
