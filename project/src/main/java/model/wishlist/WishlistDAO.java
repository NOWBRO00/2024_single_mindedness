package model.wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;

public class WishlistDAO {

	// SQL문
	// 위시리스트 추가
	private final String INSERT = "INSERT INTO WISHLIST (WISHLIST_NUM,WISHLIST_MEMBER_ID,WISHLIST_PRODUCT_NUM) VALUES (COALESCE((SELECT MAX(WISHLIST_NUM) FROM WISHLIST),0)+1,?,?)";
	// 위시리스트 제거
	private final String DELETE = "DELETE FROM WISHLIST WHERE WISHLIST_NUM = ?";
	// 나의 위시리스트 전체 보기
	private final String SELECTALL = "SELECT WD.WISHLIST_NUM, WD.WISHLIST_PRODUCT_NUM, WD.PRODUCT_NAME, WD.PRODUCT_PRICE, WD.PRODUCT_LOCATION, WD.PRODUCT_CATEGORY, WD.PRODUCT_ADDRESS, WD.FILE_NAME, WD.FILE_EXTENSION, WD.FILE_DIR\r\n"
			+ "FROM (SELECT W.WISHLIST_NUM, W.WISHLIST_PRODUCT_NUM, P.PRODUCT_NAME, P.PRODUCT_PRICE, P.PRODUCT_LOCATION, P.PRODUCT_CATEGORY, P.PRODUCT_ADDRESS, I.FILE_NAME, I.FILE_EXTENSION, I.FILE_DIR,\r\n"
			+ "			ROW_NUMBER() OVER(ORDER BY WISHLIST_NUM DESC)AS ROW_NUM\r\n"
			+ "	FROM WISHLIST W\r\n"
			+ "	JOIN PRODUCT P ON W.WISHLIST_PRODUCT_NUM = P.PRODUCT_NUM\r\n"
			+ "	LEFT JOIN (SELECT FILE_NAME, FILE_EXTENSION, FILE_DIR, PRODUCT_ITEM_NUM, ROW_NUMBER() OVER(PARTITION BY PRODUCT_ITEM_NUM ORDER BY FILE_NUM)AS RN FROM IMAGEFILE)I\r\n"
			+ "	ON P.PRODUCT_NUM = I.PRODUCT_ITEM_NUM AND I.RN=2\r\n"
			+ "	WHERE WISHLIST_MEMBER_ID = ?) WD\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 나의 위시리스트 개수 보기
	private final String SELECTONE = "SELECT COUNT(WISHLIST_NUM) AS WISHLIST_COUNT FROM WISHLIST WHERE WISHLIST_MEMBER_ID=?";
	// 위시리스트 페이지 수 보기
	private final String WISHLIST_TOTAL_PAGE = "SELECT CEIL(COUNT(WISHLIST_NUM)/9.0)AS WISHLIST_TOTAL_PAGE FROM WISHLIST WHERE WISHLIST_MEMBER_ID=?";


	public boolean insert(WishlistDTO wishlistDTO){	// 추가

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// 위시리스트 추가
		// SQL문을 읽어올 객체를 생성한다
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.WishlistDAO.insert 시작");
			// conn을 통하여 SQL문을 읽을 준비를 한다
			pstmt = conn.prepareStatement(INSERT);
			// 데이터를 입력할 값을 파라미터에 순서대로 넣는다
			// WISHLIST_MEMBER_ID,WISHLIST_PRODUCT_NUM
			pstmt.setString(1, wishlistDTO.getWishlist_member_id()); // 회원 ID
			pstmt.setInt(2, wishlistDTO.getWishlist_product_num()); // 상품 번호

			// CUD 타입 : executeUpdate로 변경이 된 행 수를 반환받는다
			int result = pstmt.executeUpdate();
			System.out.println("model.WishlistDAO.insert result : " + result);
			if(result<=0) {// 만약 변경이 된 행 수가 0보다 작거나 같다면
				System.out.println("model.WishlistDAO.insert 행 변경 실패");
				return false;
				// false 반환
			}
			System.out.println("model.WishlistDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패시에도 false 반환
			return false;
		}


		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.WishlistDAO.insert 종료");
		return true;
	}

	public boolean delete(WishlistDTO wishlistDTO){	// 삭제

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// 위시리스트 삭제
		// SQL문을 읽어올 객체를 생성한다
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.WishlistDAO.delete 시작");
			// conn을 통하여 SQL문을 읽을 준비를 한다
			pstmt = conn.prepareStatement(DELETE);
			// 데이터를 찾을 값을 파라미터에 순서대로 넣는다
			// WISHLIST_NUM
			pstmt.setInt(1, wishlistDTO.getWishlist_num()); // 위시리스트 번호 (PK)

			// CUD 타입 : executeUpdate로 변경이 된 행 수를 반환받는다
			int result = pstmt.executeUpdate();
			System.out.println("model.WishlistDAO.delete result : " + result);
			if(result<=0) {// 만약 변경이 된 행 수가 0보다 작거나 같다면
				System.out.println("model.WishlistDAO.delete 행 변경 실패");
				return false;
				// false 반환
			}
			System.out.println("model.WishlistDAO.delete 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			// SQL문 실패시에도 false 반환
			return false;
		}


		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.WishlistDAO.delete 종료");
		return true;
	}

	public ArrayList<WishlistDTO> selectAll(WishlistDTO wishlistDTO){ // 전체 출력
		ArrayList<WishlistDTO> datas=new ArrayList<WishlistDTO>();

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// 내 위시리스트 전체 출력
		// SQL문을 읽어올 객체를 생성한다
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.WishlistDAO.selectAll 시작");
			// conn을 통하여 SQL문을 읽을 준비를 한다
			
			// 쿼리 파라미터 확인
	        System.out.println("member_id: " + wishlistDTO.getWishlist_member_id());
	        System.out.println("page_num: " + wishlistDTO.getWishlist_page_num());
			
			pstmt = conn.prepareStatement(SELECTALL);
			// 데이터를 찾기 위한 값을 1번 파라미터에 담아준다.
			// WISHLIST_MEMBER_ID
			pstmt.setString(1, wishlistDTO.getWishlist_member_id()); // 회원 ID
			// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
			pstmt.setInt(2, wishlistDTO.getWishlist_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
			pstmt.setInt(3, wishlistDTO.getWishlist_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
			
			// R 타입 : executeQuery -> ResultSet 객체 반환
			ResultSet rs = pstmt.executeQuery();
			// 반복문을 사용하여 ResultSet 객체를 읽어온다 (next 메서드 사용)
			while(rs.next()) {
				System.out.println("model.WishlistDAO.selectAll while문 시작");
				// ArrayList에 담기 위해 DTO 객체 생성
				WishlistDTO data = new WishlistDTO();
				// C에게 반환해야 할 데이터를 DTO에 한 줄씩 순서대로 담아준다
				//W.WISHLIST_NUM, W.WISHLIST_PRODUCT_NUM, P.PRODUCT_NAME, P.PRODUCT_PRICE
				data.setWishlist_num(rs.getInt("WISHLIST_NUM")); // 위시리스트 번호 (PK)
				data.setWishlist_product_num(rs.getInt("WISHLIST_PRODUCT_NUM")); // 상품번호
				data.setWishlist_product_name(rs.getString("PRODUCT_NAME")); // 상품명
				data.setWishlist_product_price(rs.getInt("PRODUCT_PRICE")); // 상품 금액
				data.setWishlist_product_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소
				data.setWishlist_product_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형(낚시배,낚시터,낚시카페,수상)
				data.setWishlist_product_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
				data.setWishlist_file_name(rs.getString("FILE_NAME")); // 파일명
				data.setWishlist_file_extension(rs.getString("FILE_EXTENSION")); // 파일 확장자
				data.setWishlist_file_dir(rs.getString("FILE_DIR")); // 파일 경로;
				// ArrayList에 DTO 추가
				datas.add(data);
				System.out.println("model.WishlistDAO.selectAll datas : " + datas);
			}

		} catch (SQLException e) {
			System.err.println("SQL문 실패");
		}

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.WishlistDAO.selectAll 종료");
		return datas;
	}

	public WishlistDTO selectOne(WishlistDTO wishlistDTO){ // 한개 출력
		WishlistDTO data=null;

		// [1],[2]단계
		Connection conn = JDBCUtil.connect();

		// [3]단계
		// SQL문을 읽어올 객체를 생성한다
		PreparedStatement pstmt = null;
		try {
			System.out.println("model.WishlistDAO.selectOne 시작");
			if(wishlistDTO.getWishlist_condition().equals("WISHLIST_COUNT")) {
				System.out.println("model.WishlistDAO.selectOne WISHLIST COUNT 쿼리 읽기 시작");
				// 위시리스트 개수 출력
				// conn을 통하여 SQL문을 읽을 준비를 한다
				pstmt = conn.prepareStatement(SELECTONE);
				// 데이터를 찾기 위한 값을 1번 파라미터에 담아준다.
				// WISHLIST_MEMBER_ID
				pstmt.setString(1, wishlistDTO.getWishlist_member_id()); // 회원 ID
			}
			else if(wishlistDTO.getWishlist_condition().equals("WISHLIST_PAGE_COUNT")) {
				System.out.println("model.WishlistDAO.selectOne WISHLIST_PAGE_COUNT 쿼리 읽기 시작");
				// 위시리스트 페이지 개수 출력
				// conn을 통하여 SQL문을 읽을 준비를 한다
				pstmt = conn.prepareStatement(WISHLIST_TOTAL_PAGE);
				// 데이터를 찾기 위한 값을 1번 파라미터에 담아준다.
				// WISHLIST_MEMBER_ID
				pstmt.setString(1, wishlistDTO.getWishlist_member_id()); // 회원 ID
			}
			else {
				System.err.println("model.WishlistDAO.selectOne 쿼리 읽기 컨디션 실패");
			}

			// R 타입 : executeQuery -> ResultSet 객체 반환
			ResultSet rs = pstmt.executeQuery();
			// 반복문을 사용하여 ResultSet 객체를 읽어온다 (next 메서드 사용)
			if(rs.next()) {
				System.out.println("model.WishlistDAO.selectOne if문 시작");
				// DTO 객체 new 생성
				data=new WishlistDTO();
				if(wishlistDTO.getWishlist_condition().equals("WISHLIST_COUNT")) {	
					System.out.println("model.WishlistDAO.selectOne WISHLIST_COUNT 객체에 담기 시작");
					// 위시리스트 전체 개수 출력
					// C에게 반환해야 할 데이터를 DTO에 한 줄씩 순서대로 담아준다
					data.setWishlist_count(rs.getInt("WISHLIST_COUNT")); // 위시리스트 개수
				}
				else if(wishlistDTO.getWishlist_condition().equals("WISHLIST_PAGE_COUNT")) {
					System.out.println("model.WishlistDAO.selectOne WISHLIST_PAGE_COUNT 객체에 담기 시작");
					// 위시리스트 페이지 개수 출력
					// C에게 반환해야 할 데이터를 DTO에 한 줄씩 순서대로 담아준다
					data.setWishlist_total_page(rs.getInt("WISHLIST_TOTAL_PAGE")); // 위시리스트 페이지 개수
				}
				else {
					System.err.println("model.WishlistDAO.selectOne 객체에 담기 컨디션 실패");
				}
				System.out.println("model.WishlistDAO.selectOne data : " + data);
			}

		} catch (SQLException e) {
			System.err.println("SQL문 실패");
		}

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.WishlistDAO.selectOne 종료");
		return data;
	}


	// 기능 미사용으로 private
	private boolean update(WishlistDTO wishlistDTO){	// 생성

		return false;
	}

}
