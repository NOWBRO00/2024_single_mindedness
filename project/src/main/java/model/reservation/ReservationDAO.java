package model.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date; 
import model.common.JDBCUtil;

public class ReservationDAO {
	// SQL문 

	// 예약 생성
	private final String INSERT = "INSERT INTO RESERVATION (RESERVATION_NUM,RESERVATION_PAYMENT_NUM,RESERVATION_REGISTRATION_DATE) \r\n"
			+ "VALUES(COALESCE((SELECT MAX(RESERVATION_NUM) FROM RESERVATION),0)+1,?,?)";
	// 예약 내용 변경 (예약 상태 변경)
	private final String UPDATE = "UPDATE RESERVATION SET RESERVATION_STATUS = ? WHERE RESERVATION_NUM = ?";


	// 내 예약 전체보기
	private final String SELECTALL = "SELECT R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS, COALESCE(PR.PRODUCT_NUM,0) AS PRODUCT_NUM, COALESCE(PR.PRODUCT_NAME,'존재하지 않는 상품입니다.') AS PRODUCT_NAME, P.PAYMENT_PRICE\r\n"
			+ "FROM RESERVATION R\r\n"
			+ "JOIN PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM\r\n"
			+ "LEFT JOIN PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM\r\n"
			+ "WHERE P.PAYMENT_MEMBER_ID = ? ORDER BY R.RESERVATION_REGISTRATION_DATE DESC";

	// 내 예약 상세보기
	private final String SELECTONE = "SELECT \r\n"
			+ "    P.PAYMENT_NUM, R.RESERVATION_NUM, R.RESERVATION_REGISTRATION_DATE, R.RESERVATION_STATUS,\r\n"
			+ "    COALESCE(PR.PRODUCT_NUM, 0) AS PRODUCT_NUM,\r\n"
			+ "    COALESCE(PR.PRODUCT_NAME, '존재하지 않는 상품입니다.') AS PRODUCT_NAME,\r\n"
			+ "    P.PAYMENT_PRICE, P.PAYMENT_REGISTRATION_DATE, P.PAYMENT_METHOD, P.PAYMENT_STATUS,\r\n"
			+ "    COALESCE(P.PAYMENT_MEMBER_ID, '탈퇴한 회원입니다.') AS PAYMENT_MEMBER_ID,\r\n"
			+ "    COALESCE(M.MEMBER_NAME, '탈퇴한 회원입니다.') AS MEMBER_NAME,\r\n"
			+ "    COALESCE(M.MEMBER_PHONE, '탈퇴한 회원입니다.') AS MEMBER_PHONE,\r\n"
			+ "    F.FILE_NAME, F.FILE_EXTENSION, F.FILE_DIR\r\n"
			+ "FROM \r\n"
			+ "    RESERVATION R\r\n"
			+ "JOIN \r\n"
			+ "    PAYMENT P ON R.RESERVATION_PAYMENT_NUM = P.PAYMENT_NUM\r\n"
			+ "LEFT JOIN \r\n"
			+ "    PRODUCT PR ON P.PAYMENT_PRODUCT_NUM = PR.PRODUCT_NUM\r\n"
			+ "LEFT JOIN \r\n"
			+ "    MEMBER M ON P.PAYMENT_MEMBER_ID = M.MEMBER_ID\r\n"
			+ "LEFT JOIN (\r\n"
			+ "    SELECT \r\n"
			+ "        FILE_NAME, \r\n"
			+ "        FILE_EXTENSION, \r\n"
			+ "        FILE_DIR,\r\n"
			+ "        PRODUCT_ITEM_NUM,\r\n"
			+ "        ROW_NUMBER() OVER (PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM) AS ROW_NUM\r\n"
			+ "    FROM IMAGEFILE\r\n"
			+ ") F ON F.ROW_NUM = 1 AND PR.PRODUCT_NUM = F.PRODUCT_ITEM_NUM\r\n"
			+ "WHERE R.RESERVATION_NUM = ?";
	// 가장 마지막에 저장된 PK 번호 보여주기
	private final String SELECTONE_LAST_NUM="SELECT RESERVATION_NUM FROM (SELECT RESERVATION_NUM FROM RESERVATION ORDER BY RESERVATION_NUM DESC) WHERE ROWNUM=1";


	public boolean insert(ReservationDTO reservationDTO){	// 추가
		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// SQL 쿼리를 읽어오기 위한 객체 생성 (PreparedStatement)
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.reservationDTO.insert 시작");
			// conn을 사용하여 SQL 쿼리를 읽어올 준비를 한다
			pstmt = conn.prepareStatement(INSERT);
			//파라미터에 값을 순서대로 넣어준다. 
			// (RESERVATION_NUM,RESERVATION_PAYMENT_NUM,RESERVATION_REGISTRATION_DATE,RESERVATION_STATUS)
			pstmt.setInt(1, reservationDTO.getReservation_payment_num()); // 결제 번호

			//pstmt.setDate(2, reservationDTO.getReservation_registration_date()); // 예약일
			// 예약일 import java.sql.Date; 에서 String 처리가 어려워 java.util.Date; 으로 변경
			// util을 사용하면 객체를 sql.Date 객체로 변환이 필요함
			Date utilDate = reservationDTO.getReservation_registration_date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // 변환
			pstmt.setDate(2, sqlDate); // 변환한 예약일

			// CUD 타입은 executeUpdate (변경이 된 행 수 : 정수로 반환)
			int result = pstmt.executeUpdate();
			System.out.println("model.reservationDTO.insert result : " + result);
			if(result<=0) { // 만약 executeUpdate를 통하여 반환받은 정수가 0보다 작거나 같다면
				// 변경된 행 수가 없는 것이므로 false 반환
				System.out.println("model.reservationDTO.insert 행 변경 실패");
				return false;
			}
			System.out.println("model.reservationDTO.insert 행 변경 성공");

		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패시에도 false 반환
			return false;
		}

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.reservationDTO.insert 종료");
		return true;
	}


	public boolean update(ReservationDTO reservationDTO){	// 변경
		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// SQL 쿼리를 읽어오기 위한 객체 생성 (PreparedStatement)
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.reservationDTO.update 시작");
			// conn을 사용하여 SQL 쿼리를 읽어올 준비를 한다
			pstmt = conn.prepareStatement(UPDATE);
			// 값을 변경하고, 찾을 데이터를 파라미터에 넣어준다.
			pstmt.setString(1, reservationDTO.getReservation_status()); // 예약 상태(예약 완료,예약취소) - 변경할 데이터
			pstmt.setInt(2, reservationDTO.getReservation_num()); // 예약 번호 - 찾을 데이터
			// CUD 타입은 executeUpdate (변경이 된 행 수 : 정수로 반환)
			int result = pstmt.executeUpdate();
			System.out.println("model.reservationDTO.update result : " + result);
			if(result<=0) { // 만약 executeUpdate를 통하여 반환받은 정수가 0보다 작거나 같다면
				// 변경된 행 수가 없는 것이므로 false 반환
				System.out.println("model.reservationDTO.update 행 변경 실패");
				return false;
			}
			System.out.println("model.reservationDTO.update 행 변경 성공");

		} catch (SQLException e) {
			System.out.println("SQL문 실패");
			// SQL문 실패시에도 false 반환
			return false;
		}

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.reservationDTO.update 종료");
		return true;
	}


	public ArrayList<ReservationDTO> selectAll(ReservationDTO reservationDTO){ // 전체 출력
		ArrayList<ReservationDTO> datas=new ArrayList<ReservationDTO>();

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// SQL 쿼리를 읽어오기 위한 객체 생성 (PreparedStatement)
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.reservationDTO.selectAll 시작");
			// conn을 사용하여 SQL 쿼리를 읽어올 준비를 한다
			// 내 예약 내역 전체보기
			pstmt = conn.prepareStatement(SELECTALL);
			// 값을 찾을 데이터를 파라미터에 넣어준다.
			pstmt.setString(1, reservationDTO.getReservation_member_id()); // 회원 ID

			// SELECT (R) 타입은 executeQuery : ResultSet 객체 반환함
			ResultSet rs = pstmt.executeQuery();
			// next 메서드를 이용하여 한 줄씩 읽어온다.
			while(rs.next()) {
				System.out.println("model.reservationDTO.selectAll while문 시작");
				// 한번에 전달해주기 위해, DTO 객체 생성
				ReservationDTO data = new ReservationDTO();
				// DTO에 하나씩 담아준다
				data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호

				//data.setReservation_registration_date(rs.getDate("RESERVATION_REGISTRATION_DATE")); // 예약일 (상품 이용일)
				// DB에는 날짜가 DATE 타입으로 저장되며, JDBC에서는 이 값을 java.sql.Date 클래스로 가지고 온다.
				// DTO에서는 java.util.Date를 사용하여 날짜를 처리하고 있기 때문에 변환 필요
				java.sql.Date sqlDate = rs.getDate("RESERVATION_REGISTRATION_DATE"); // DB에 저장된 예약일
				//DTO 객체에 sql 클래스로 가지고 온 예약일을 util 타입으로 변환하여 담아준다.
				data.setReservation_registration_date(new Date(sqlDate.getTime())); // 예약일 (상품 이용일)

				data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
				data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
				data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
				data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액				
				// 담아준 DTO 데이터를 ArrayList에 추가한다
				datas.add(data);
				System.out.println("model.reservationDTO.selectAll datas : " + datas);
			}

		} catch (SQLException e) {
			System.out.println("SQL문 실패");
		}


		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.reservationDTO.selectAll 종료");
		return datas;
	}


	public ReservationDTO selectOne(ReservationDTO reservationDTO){ // 한개 출력
		ReservationDTO data=null;

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// SQL 쿼리를 읽어오기 위한 객체 생성 (PreparedStatement)
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.reservationDTO.selectOne 시작");
			// conn을 사용하여 SQL 쿼리를 읽어올 준비를 한다

			if(reservationDTO.getReservation_condition().equals("RESERVATION_SELECTONE")) {
				// 내 예약 내역 상세보기
				System.out.println("model.reservationDTO.selectOne 내 예약 상세보기 컨디션 성공");
				pstmt = conn.prepareStatement(SELECTONE);
				// 값을 찾을 데이터를 파라미터에 넣어준다.
				pstmt.setInt(1, reservationDTO.getReservation_num()); // 예약 번호 (PK)				
			}
			else if(reservationDTO.getReservation_condition().equals("RESERVATION_LAST_NUM")) {
				// 가장 마지막 pk 번호 받아오기
				System.out.println("model.reservationDTO.selectOne 가장 마지막 PK 번호 받아오기 컨디션 성공");
				pstmt = conn.prepareStatement(SELECTONE_LAST_NUM);
			}
			else {
				System.out.println("model.reservationDTO.selectOne 컨디션 실패");		
			}
			// SELECT (R) 타입은 executeQuery : ResultSet 객체 반환함
			ResultSet rs = pstmt.executeQuery();
			// next 메서드를 이용하여 한 줄씩 읽어온다.
			// selectOne으로 하나만 받아오니까 while이 아닌 if문
			if(rs.next()) {
				data=new ReservationDTO();
				System.out.println("model.reservationDTO.selectOne rs.next() 시작");

				if(reservationDTO.getReservation_condition().equals("RESERVATION_SELECTONE")) {
					System.out.println("model.reservationDTO.selectOne 내 예약 상세보기 객체에 담기 시작");
					// DTO에 하나씩 담아준다
					data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
					data.setReservation_registration_date(rs.getDate("RESERVATION_REGISTRATION_DATE")); // 예약일 (상품 이용일)
					data.setReservation_status(rs.getString("RESERVATION_STATUS")); // 예약 상태 (예약 완료, 예약 취소)
					data.setReservation_product_num(rs.getInt("PRODUCT_NUM")); // 예약 상품 번호
					data.setReservation_product_file_dir(rs.getString("FILE_DIR")); // 예약 상품 썸네일
					data.setReservation_product_name(rs.getString("PRODUCT_NAME")); // 예약 상품명
					data.setReservation_price(rs.getInt("PAYMENT_PRICE")); // 예약(결제) 금액
					data.setReservation_payment_date(rs.getDate("PAYMENT_REGISTRATION_DATE")); // 결제일
					data.setReservation_payment_num(rs.getInt("PAYMENT_NUM")); // 결제 번호
					data.setReservation_payment_method(rs.getString("PAYMENT_METHOD")); // 결제 방법 (카드, 페이)
					data.setReservation_payment_status(rs.getString("PAYMENT_STATUS")); // 결제 상태 (결제 완료, 결제 취소)
					data.setReservation_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 결제자  ID
					data.setReservation_member_name(rs.getString("MEMBER_NAME")); // 예약자 성명
					data.setReservation_member_phone(rs.getString("MEMBER_PHONE")); // 예약자 핸드폰 번호
				}
				else if(reservationDTO.getReservation_condition().equals("RESERVATION_LAST_NUM")) {
					System.out.println("model.reservationDTO.selectOne 가장 마지막 PK번호 객체에 담기 시작");
					data.setReservation_num(rs.getInt("RESERVATION_NUM")); // 예약 번호
				}
				else {
					System.out.println("model.reservationDTO.selectOne 객체에 담기 컨디션 실패");		
				}
				System.out.println("model.reservationDTO.selectOne 로그 data : " + data);			
			}

		} catch (SQLException e) {
			System.err.println("SQL문 실패");
		}

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.reservationDTO.selectOne 종료");
		return data;
	}


	// 기능 미사용으로 private 
	private boolean delete(ReservationDTO reservationDTO){	// 삭제

		return false;
	}
}
