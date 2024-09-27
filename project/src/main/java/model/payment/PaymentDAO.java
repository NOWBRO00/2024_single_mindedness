package model.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;

public class PaymentDAO {

	// 결제 생성
	private final String INSERT = "INSERT INTO PAYMENT (PAYMENT_NUM,PAYMENT_MEMBER_ID,PAYMENT_PRODUCT_NUM,PAYMENT_ORDER_NUM,MERCHANT_UID,PAYMENT_PRICE,PAYMENT_STATUS,PAYMENT_METHOD)\r\n"
			+ "VALUES(COALESCE((SELECT MAX(PAYMENT_NUM) FROM PAYMENT),0)+1,?,?,?,?,?,?,?)";
	// 결제 변경 (삭제 없으므로 결제 상태 변경)
	private final String UPDATE = "UPDATE PAYMENT SET PAYMENT_STATUS =? WHERE PAYMENT_NUM=?";
	// 결제 내역 반환
	private final String SELECTONE_BY_MERCHANT_UID = "SELECT PAYMENT_NUM,PAYMENT_MEMBER_ID,PAYMENT_PRODUCT_NUM,PAYMENT_ORDER_NUM,MERCHANT_UID,PAYMENT_REGISTRATION_DATE,PAYMENT_PRICE,PAYMENT_STATUS,PAYMENT_METHOD\r\n"
			+ "FROM PAYMENT\r\n"
			+ "WHERE MERCHANT_UID=?";
	private final String SELECTONE_BY_PAYMENT_NUM = "SELECT PAYMENT_NUM,PAYMENT_MEMBER_ID,PAYMENT_PRODUCT_NUM,PAYMENT_ORDER_NUM,MERCHANT_UID,PAYMENT_REGISTRATION_DATE,PAYMENT_PRICE,PAYMENT_STATUS,PAYMENT_METHOD FROM PAYMENT WHERE PAYMENT_NUM = ?";


	public boolean insert(PaymentDTO paymentDTO){	// 입력
		// [1],[2] 단계
		// JDBC 연결
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		// 쿼리를 읽어오기 위한 객체를 선언한다
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.PaymentDAO.insert 시작");
			// conn을 통하여 SQL 쿼리를 읽어온다
			pstmt=conn.prepareStatement(INSERT);
			// 파라미터에 값을 순서대로 넣어준다.
			pstmt.setString(1, paymentDTO.getPayment_member_id()); // 결제자 ID (PK)
			pstmt.setInt(2, paymentDTO.getPayment_product_num()); // 결제 상품 번호
			pstmt.setString(3, paymentDTO.getPayment_order_num()); // 주문 번호 (포트원 생성 번호)
			pstmt.setString(4, paymentDTO.getMerchant_uid()); // UUID + 시간 조합 번호 (controller 사용)
			pstmt.setInt(5, paymentDTO.getPayment_price()); // 결제 금액
			pstmt.setString(6, paymentDTO.getPayment_status()); // 결제 상태 : 결제완료, 결제취소
			pstmt.setString(7, paymentDTO.getPayment_method()); // 결제 방법 : 카드, 페이


			// CUD 타입은 executeUpdate >> 변경이 된 행 수를 정수로 반환
			int result = pstmt.executeUpdate();
			System.out.println("model.PaymentDAO.insert result : " + result);
			if(result<=0) {// 변경이 된 행 수가 0보다 작거나 같다면
				System.out.println("model.PaymentDAO.insert 행 변경 실패..");
				// false 반환
				return false;
			}
			System.out.println("model.PaymentDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			e.printStackTrace();
			// 실패시에도 false 반환
			return false;
		}


		// [4] 단계
		// 연결해제
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.PaymentDAO.update 종료");

		return true;
	}

	public boolean update(PaymentDTO paymentDTO){	// 변경
		// [1],[2] 단계
		// JDBC 연결
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		// 쿼리를 읽어오기 위한 객체를 선언한다
		PreparedStatement pstmt = null;

		try {
			System.out.println("model.PaymentDAO.update 시작");
			// conn을 통하여 SQL문을 읽어올 준비를 한다.
			pstmt=conn.prepareStatement(UPDATE);
			// 값을 변경하고 찾기 위해 파라미터에 값을 넣어준다.
			pstmt.setString(1, paymentDTO.getPayment_status()); // 결제 상태 : 결제 완료, 결제 취소
			pstmt.setInt(2, paymentDTO.getPayment_num()); // 결제 PK

			// executeUpdate를 통하여 변경이 된 행 수를 반환받는다
			int result = pstmt.executeUpdate();
			System.out.println("model.PaymentDAO.update result : "+result);
			if(result<=0) { // 만약 변경된 행이 없다면
				System.out.println("model.PaymentDAO.update 행 변경 실패");
				// false 반환
				return false;
			}
			System.out.println("model.PaymentDAO.update 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패일때도 false 반환
			return false;
		}

		// [4] 단계
		// 연결해제
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.PaymentDAO.update 종료");
		return true;
	}


	public PaymentDTO selectOne(PaymentDTO paymentDTO){// 한명 출력
		PaymentDTO data=null;

		// [1],[2] 단계
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		// 쿼리를 읽어오기 위한 객체를 선언한다
		PreparedStatement pstmt = null;

		try {
			System.out.println("model.PaymentDTO.selectOne 시작");
			System.out.println(paymentDTO.getPayment_condition());
			// conn을 통해 SQL문을 읽어올 준비를 한다
			if(paymentDTO.getPayment_condition().equals("SELECT_BY_MERCHANT_UID")){
				pstmt = conn.prepareStatement(SELECTONE_BY_MERCHANT_UID);
				// 파라미터에 찾을 데이터를 넣어준다.
				pstmt.setString(1, paymentDTO.getMerchant_uid());
			}
			else if(paymentDTO.getPayment_condition().equals("SELECT_BY_PAYMENT_NUM")) {
				pstmt = conn.prepareStatement(SELECTONE_BY_PAYMENT_NUM);
				// 파라미터에 찾을 데이터를 넣어준다.
				pstmt.setInt(1, paymentDTO.getPayment_num()); // 컨트롤러에서 사용하는 번호
			}

			// executeQuery로 ResultSet 객체를 반환받는다
			ResultSet rs = pstmt.executeQuery();
			// ResultSet 객체를 next() 메서드를 통해 한 줄씩 읽어온다. 이때, selectOne이기 때문에 if문 사용
			if(rs.next()) {
				System.out.println("model.PaymentDTO.selectOne rs.next() 시작");

				// C에게 반환하기 위해 DTO 객체를 new 해준다.
				data = new PaymentDTO();
				// data에 보내줄 데이터를 순서대로 담아준다.
				data.setPayment_num(rs.getInt("PAYMENT_NUM")); // 결제 번호
				data.setPayment_member_id(rs.getString("PAYMENT_MEMBER_ID")); // 결제자 ID
				data.setPayment_product_num(rs.getInt("PAYMENT_PRODUCT_NUM")); // 결제한 상품 번호
				data.setPayment_order_num(rs.getString("PAYMENT_ORDER_NUM")); // 포트원에서 생성된 결제 번호
				data.setMerchant_uid(rs.getString("MERCHANT_UID")); // controller에서 UUID+시간 조합으로 사용하는 번호
				data.setPayment_registration_date(rs.getDate("PAYMENT_REGISTRATION_DATE")); // 결제일
				data.setPayment_price(rs.getInt("PAYMENT_PRICE")); // 결제 금액
				data.setPayment_status(rs.getString("PAYMENT_STATUS")); // 결제 상태 (결제완료, 결제 취소)
				data.setPayment_method(rs.getString("PAYMENT_METHOD")); // 결제 방법 (card, pay)
			}
			System.out.println("model.PaymentDTO.selectOne data : " + data);

		} catch (SQLException e) {
			System.err.println("SQL문 실패..");
			e.printStackTrace();
		}

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.PaymentDTO.selectOne 종료");

		return data;
	}



	// 기능 미사용으로 private
	private boolean delete(PaymentDTO paymentDTO){	// 입력

		return false;
	}
	// 기능 미사용으로 private
	private ArrayList<PaymentDTO> selectAll(PaymentDTO paymentDTO){ // 전체 출력
		ArrayList<PaymentDTO> datas=new ArrayList<PaymentDTO>();
		return datas;
	}

}
