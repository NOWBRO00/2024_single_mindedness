package model.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;

public class ReviewDAO {

	// SQL문
	// 리뷰 작성
	private final String INSERT = "INSERT INTO REVIEW (REVIEW_NUM, REVIEW_CONTENT, REVIEW_PRODUCT_NUM, REVIEW_WRITER_ID, REVIEW_STAR) VALUES(COALESCE((SELECT MAX(REVIEW_NUM) \r\n"
			+ "FROM REVIEW),0)+1, ?,?,?,?)";
	// 리뷰 수정
	private final String UPDATE = "UPDATE REVIEW SET REVIEW_CONTENT=?, REVIEW_STAR=? WHERE REVIEW_NUM=?";
	// 리뷰 삭제
	private final String DELETE = "DELETE FROM REVIEW WHERE REVIEW_NUM=?";

	// 리뷰 전체 출력 (최신순)
	private final String SELECTALL_NEW_REVIEW = "SELECT R.REVIEW_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR, R.REVIEW_WRITER_ID, M.MEMBER_NICKNAME,COALESCE(ML.LV_COUNT,1) AS MEMBER_LEVEL, M.MEMBER_PROFILE\r\n"
			+ "FROM REVIEW R\r\n"
			+ "JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID\r\n"
			+ "LEFT JOIN (SELECT B.BOARD_WRITER_ID,COUNT(B.BOARD_WRITER_ID) AS LV_COUNT FROM BOARD B GROUP BY B.BOARD_WRITER_ID)ML ON R.REVIEW_WRITER_ID=ML.BOARD_WRITER_ID\r\n"
			+ "WHERE R.REVIEW_PRODUCT_NUM = ? ORDER BY R.REVIEW_NUM DESC";
	// 리뷰 전체 출력 (별점 높은 순)
	private final String SELECTALL_HIGH_STAR = "SELECT R.REVIEW_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR, R.REVIEW_WRITER_ID, M.MEMBER_NICKNAME,COALESCE(ML.LV_COUNT,1) AS MEMBER_LEVEL, M.MEMBER_PROFILE\r\n"
			+ "FROM REVIEW R\r\n"
			+ "JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID\r\n"
			+ "LEFT JOIN (SELECT B.BOARD_WRITER_ID,COUNT(B.BOARD_WRITER_ID) AS LV_COUNT FROM BOARD B GROUP BY B.BOARD_WRITER_ID)ML ON R.REVIEW_WRITER_ID=ML.BOARD_WRITER_ID\r\n"
			+ "WHERE R.REVIEW_PRODUCT_NUM = ? ORDER BY R.REVIEW_STAR DESC";
	// 리뷰 전체 출력 (별점 낮은 순)
	private final String SELECTALL_LOW_STAR = "SELECT R.REVIEW_NUM, R.REVIEW_CONTENT, R.REVIEW_REGISTRATION_DATE, R.REVIEW_STAR, R.REVIEW_WRITER_ID, M.MEMBER_NICKNAME,COALESCE(ML.LV_COUNT,1) AS MEMBER_LEVEL, M.MEMBER_PROFILE\r\n"
			+ "FROM REVIEW R\r\n"
			+ "JOIN MEMBER M ON R.REVIEW_WRITER_ID = M.MEMBER_ID\r\n"
			+ "LEFT JOIN (SELECT B.BOARD_WRITER_ID,COUNT(B.BOARD_WRITER_ID) AS LV_COUNT FROM BOARD B GROUP BY B.BOARD_WRITER_ID)ML ON R.REVIEW_WRITER_ID=ML.BOARD_WRITER_ID\r\n"
			+ "WHERE R.REVIEW_PRODUCT_NUM = ? ORDER BY R.REVIEW_STAR";


	public boolean insert(ReviewDTO reviewDTO){	// 추가

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.ReviewDAO.insert 시작");
			// conn을 통하여 SQL문을 읽어올 준비를 한다 (try-catch 필요)
			// 리뷰 입력
			pstmt=conn.prepareStatement(INSERT);
			// 파라미터에 담아줄 값 REVIEW_CONTENT, REVIEW_PRODUCT_NUM, REVIEW_WRITER_ID, REVIEW_STAR
			pstmt.setString(1, reviewDTO.getReview_content()); // 리뷰 내용
			pstmt.setInt(2, reviewDTO.getReview_product_num()); // 리뷰를 쓴 상품 번호
			pstmt.setString(3, reviewDTO.getReview_writer_id()); // 작성자 ID
			pstmt.setInt(4, reviewDTO.getReview_star()); // 별점 (1~5)

			// CUD 타입 == executeUpdate, 변경이 된 행 수를 정수로 반환해주기 때문에 int 타입 변수에 넣어준다.
			int result = pstmt.executeUpdate();
			System.out.println("model.ReviewDAO.insert result : " + result);
			// 만약 변경된 행 수가 0보다 작거나 같다면
			if(result<=0) {
				System.out.println("model.ReviewDAO.insert 변경 실패 ");
				// false를 반환
				return false;
			}
			System.out.println("model.ReviewDAO.insert 변경 성공 ");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패해도 false 반환
			return false;
		}


		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReviewDAO.insert 종료");
		return true;
	}


	public boolean update(ReviewDTO reviewDTO){	// 변경

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.ReviewDAO.update 시작");
			// conn을 통하여 SQL문을 읽어올 준비를 한다 (try-catch 필요)
			// 리뷰 수정
			pstmt=conn.prepareStatement(UPDATE);
			// 파라미터에 담아줄 값 REVIEW_CONTENT, REVIEW_STAR, REVIEW_NUM
			pstmt.setString(1, reviewDTO.getReview_content()); // 리뷰 내용
			pstmt.setInt(2, reviewDTO.getReview_star()); // 별점 (1~5)
			pstmt.setInt(3, reviewDTO.getReview_num()); // 리뷰 번호 (선택용)

			// CUD 타입 == executeUpdate, 변경이 된 행 수를 정수로 반환해주기 때문에 int 타입 변수에 넣어준다.
			int result = pstmt.executeUpdate();
			System.out.println("model.ReviewDAO.update result : " + result);
			// 만약 변경된 행 수가 0보다 작거나 같다면
			if(result<=0) {
				System.out.println("model.ReviewDAO.update 변경 실패 ");
				// false를 반환
				return false;
			}
			System.out.println("model.ReviewDAO.update 변경 성공 ");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패해도 false 반환
			return false;
		}


		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReviewDAO.update 종료");
		return true;
	}

	public boolean delete(ReviewDTO reviewDTO){	// 삭제


		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.ReviewDAO.delete 시작");
			// conn을 통하여 SQL문을 읽어올 준비를 한다 (try-catch 필요)
			// 리뷰 삭제
			pstmt=conn.prepareStatement(DELETE);
			// 파라미터에 담아줄 값 REVIEW_NUM
			pstmt.setInt(1, reviewDTO.getReview_num()); // 리뷰 번호 (선택용)

			// CUD 타입 == executeUpdate, 변경이 된 행 수를 정수로 반환해주기 때문에 int 타입 변수에 넣어준다.
			int result = pstmt.executeUpdate();
			System.out.println("model.ReviewDAO.delete result : " + result);
			// 만약 변경된 행 수가 0보다 작거나 같다면
			if(result<=0) {
				System.out.println("model.ReviewDAO.delete 변경 실패 ");
				// false를 반환
				return false;
			}
			System.out.println("model.ReviewDAO.delete 변경 성공 ");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패해도 false 반환
			return false;
		}

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReviewDAO.delete 종료");
		return true;
	}

	public ArrayList<ReviewDTO> selectAll(ReviewDTO reviewDTO){ // 전체 출력
		ArrayList<ReviewDTO> datas=new ArrayList<ReviewDTO>();

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.ReviewDAO.selectAll 시작");
			if(reviewDTO.getReview_condition().equals("REVIEW_ALL")) { // 리뷰 전체 출력 (최신순)
				pstmt=conn.prepareStatement(SELECTALL_NEW_REVIEW);
				System.out.println("model.ReviewDAO.selectAll REVIEW_ALL 시작");
			}
			else if(reviewDTO.getReview_condition().equals("REVIEW_HIGH")) { // 리뷰 전체 출력 (별점 높은 순)
				pstmt=conn.prepareStatement(SELECTALL_HIGH_STAR);
				System.out.println("model.ReviewDAO.selectAll REVIEW_HIGH 시작");
			}
			else if(reviewDTO.getReview_condition().equals("REVIEW_LOW")) { // 리뷰 전체 출력 (별점 낮은 순)
				pstmt=conn.prepareStatement(SELECTALL_LOW_STAR);
				System.out.println("model.ReviewDAO.selectAll REVIEW_LOW 시작");
			}
			else {
				System.out.println("model.ReviewDAO.selectAll 컨디션 실패");
			}

			// 상품과 함께 띄워줘야 해서 REVIEW_PRODUCT_NUM 으로 검색 필요
			// selectAll 모두 동일하므로 if문 바깥으로 빼줌
			pstmt.setInt(1, reviewDTO.getReview_product_num()); // 상품 글 번호

			// R(select)타입 : executeQuery - ResultSet 객체로 반환해줘서, 해당 객체를 한 줄씩 읽어와야함
			ResultSet rs = pstmt.executeQuery();
			// while문을 사용하여 반환된 ResultSet 객체를 읽어온다. (next() 메서드 사용)
			while(rs.next()) {
				System.out.println("model.ReviewDAO.selectAll while문 시작");
				// 한번에 담아서 ArrayList로 전달해줘야 하므로, DTO 타입 new 생성
				ReviewDTO data = new ReviewDTO();
				// 한 줄씩 값을 담아준다
				data.setReview_num(rs.getInt("REVIEW_NUM")); // 리뷰 번호 (PK)
				data.setReview_content(rs.getString("REVIEW_CONTENT")); // 리뷰 내용
				data.setReview_registration_date(rs.getDate("REVIEW_REGISTRATION_DATE")); // 리뷰 작성일
				data.setReview_star(rs.getInt("REVIEW_STAR")); // 별점 (1~5)
				data.setReview_writer_id(rs.getString("REVIEW_WRITER_ID")); // 작성자 아이디
				data.setReview_writer_nickname(rs.getString("MEMBER_NICKNAME")); // 작성자 닉네임
				data.setReview_writer_level(rs.getInt("MEMBER_LEVEL")); // 작성자 레벨
				data.setReview_member_profile(rs.getString("MEMBER_PROFILE")); // 작성자 프로필

				// 마지막에는 AL에 담아준다 (반환하기 위해)
				datas.add(data);	
				System.out.println("model.ReviewDAO.selectAll datas : " + datas);
			}

		} catch(SQLException e) {
			System.err.println("SQL문 실패..");
		}

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ReviewDAO.selectAll 종료");
		return datas;
	}




	// 기능 미구현으로 private 처리함
	private ReviewDTO selectOne(ReviewDTO reviewDTO){ // 한개 출력
		ReviewDTO data=null;

		return data;
	}
}
