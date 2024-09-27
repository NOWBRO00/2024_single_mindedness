package model.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;




public class ProductDAO {

	// SQL문
	// 상품 전체 출력 (최신순)
	private final String SELECTALL = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM (SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
			+ "	ROW_NUMBER() OVER (ORDER BY PRODUCT_NUM) AS ROW_NUM\r\n"
			+ "	FROM PRODUCT_INFO_VIEW)\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 별점 높은 순으로 출력
	private final String SELECTALL_RATING = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM (SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
			+ "			ROW_NUMBER() OVER (ORDER BY COALESCE(RATING,-1) DESC) AS ROW_NUM\r\n"
			+ "			FROM PRODUCT_INFO_VIEW)\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 예약(결제) 많은 순으로 출력
	private final String SELECTALL_RESERVATION = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM (SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
			+ "			ROW_NUMBER() OVER (ORDER BY COALESCE(PAYMENT_COUNT,-1) DESC) AS ROW_NUM\r\n"
			+ "			FROM PRODUCT_INFO_VIEW)\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 찜 많은 순으로 출력
	private final String SELECTALL_WISHLIST = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM (SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
			+ "			ROW_NUMBER() OVER (ORDER BY COALESCE(WISHLIST_COUNT,-1) DESC) AS ROW_NUM\r\n"
			+ "			FROM PRODUCT_INFO_VIEW)\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 상품명으로 검색하여 출력
	private final String SELECTALL_SEARCHKEYWORD = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM (SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
			+ "			ROW_NUMBER() OVER (ORDER BY PRODUCT_NUM DESC) AS ROW_NUM\r\n"
			+ "			FROM PRODUCT_INFO_VIEW WHERE PRODUCT_NAME LIKE '%'||?||'%')\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
	// 필터링 검색 기능 (장소(바다/민물), 유형(낚시배,낚시터,낚시카페, 수상))
	private final String SELECTALL_FILTERING = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM (SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY, RATING, PAYMENT_COUNT, WISHLIST_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
			+ "			ROW_NUMBER() OVER (ORDER BY PRODUCT_NUM DESC) AS ROW_NUM\r\n"
			+ "			FROM PRODUCT_INFO_VIEW\r\n"
			+ "			WHERE (PRODUCT_LOCATION = COALESCE(?,PRODUCT_LOCATION)) AND (PRODUCT_CATEGORY = COALESCE(?,PRODUCT_CATEGORY)))\r\n"
			+ "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";


	// 상품 상세보기
	private final String SELECTONE = "SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY,\r\n"
			+ "(SELECT COALESCE(ROUND(AVG(R.REVIEW_STAR),1),0) FROM REVIEW R WHERE R.REVIEW_PRODUCT_NUM=PRODUCT_NUM) AS RATING,\r\n"
			+ "(SELECT COUNT(PA.PAYMENT_PRODUCT_NUM) FROM PAYMENT PA WHERE PA.PAYMENT_PRODUCT_NUM=PRODUCT_NUM) AS PAYMENT_COUNT,\r\n"
			+ "(SELECT COUNT(W.WISHLIST_PRODUCT_NUM) FROM WISHLIST W WHERE W.WISHLIST_PRODUCT_NUM=PRODUCT_NUM) AS WISHLIST_COUNT\r\n"
			+ "FROM PRODUCT\r\n"
			+ "WHERE PRODUCT_NUM = ?";
	// 사용자가 선택한 일자의 재고 보기
	private final String SELECTONE_CURRENT_STOCK = "SELECT P.PRODUCT_NUM, (P.PRODUCT_CNT - COALESCE(RS.RESERVATION_COUNT,0)) AS CURRENT_STOCK\r\n"
			+ "FROM PRODUCT P\r\n"
			+ "LEFT JOIN(SELECT PA.PAYMENT_PRODUCT_NUM AS PRODUCT_NUM, COUNT(R.RESERVATION_REGISTRATION_DATE) AS RESERVATION_COUNT FROM RESERVATION R JOIN PAYMENT PA ON R.RESERVATION_PAYMENT_NUM= PA.PAYMENT_NUM WHERE R.RESERVATION_REGISTRATION_DATE=? GROUP BY PA.PAYMENT_PRODUCT_NUM)RS ON P.PRODUCT_NUM=RS.PRODUCT_NUM\r\n"
			+ "WHERE P.PRODUCT_NUM = ?";
	// 전체 데이터 개수를 반환 (전체 페이지 수 - 기본)
	private final String PRODUCT_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(PRODUCT_NUM),0)/9.0)AS PRODUCT_TOTAL_PAGE FROM PRODUCT WHERE 1=1";
	// 전체 데이터 개수를 반환 (검색어 사용 페이지수 )
	private final String PRODUCT_SEARCH_PAGE = "AND PRODUCT_NAME LIKE '%'||?||'%'";
	// 전체 데이터 개수를 반환 (필터링 검색 페이지 수)
	private final String PRODUCT_FILTERING_PAGE = "AND PRODUCT_LOCATION = COALESCE(?,PRODUCT_LOCATION) AND PRODUCT_CATEGORY = COALESCE(?,PRODUCT_CATEGORY)";
	

	// 샘플데이터(크롤링) insert
	// 바다
	//낚시배
	private final String PRODUCT_CRAWLING_SEA_BOAR_INSERT="INSERT INTO PRODUCT (PRODUCT_NUM,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_DETAILS,PRODUCT_CNT,PRODUCT_ADDRESS,PRODUCT_LOCATION,PRODUCT_CATEGORY)\r\n"
			+ "VALUES ((SELECT NVL(MAX(PRODUCT_NUM),0)+1 FROM PRODUCT),?,?,?,?,?,'바다','낚시배')";	
	//낚시터
	private final String PRODUCT_CRAWLING_SEA_FISHING_INSERT = "INSERT INTO PRODUCT (PRODUCT_NUM,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_DETAILS,PRODUCT_CNT,PRODUCT_ADDRESS,PRODUCT_LOCATION,PRODUCT_CATEGORY)\r\n"
			+ "VALUES ((SELECT NVL(MAX(PRODUCT_NUM),0)+1 FROM PRODUCT),?,?,'바다 낚시터입니다~!',99,?,'바다','낚시터')";

	// 민물
	//낚시터
	private final String PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT ="INSERT INTO PRODUCT (PRODUCT_NUM,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_DETAILS,PRODUCT_CNT,PRODUCT_ADDRESS,PRODUCT_LOCATION,PRODUCT_CATEGORY)\r\n"
			+ "VALUES ((SELECT NVL(MAX(PRODUCT_NUM),0)+1 FROM PRODUCT),?,?,'민물 낚시터입니다~!',?,?,'민물','수상')";
	//낚시카페
	private final String PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT="INSERT INTO PRODUCT (PRODUCT_NUM,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_DETAILS,PRODUCT_CNT,PRODUCT_ADDRESS,PRODUCT_LOCATION,PRODUCT_CATEGORY)\r\n"
			+ "VALUES ((SELECT NVL(MAX(PRODUCT_NUM),0)+1 FROM PRODUCT),?,?,'민물 낚시카페입니다~!',50,?,'민물','낚시카페')";

	// 이미지 파일 저장을 위한 select
	// 상품 pk 출력
	private final String PRODUCT_NUM_SELECT="SELECT MAX(PRODUCT_NUM) AS MAX_NUM FROM PRODUCT";
	// 상품 전체 출력
	private final String PRODUCT_SELECT="SELECT PRODUCT_NUM, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DETAILS, PRODUCT_CNT, PRODUCT_ADDRESS, PRODUCT_LOCATION, PRODUCT_CATEGORY FROM PRODUCT";


	public boolean insert(ProductDTO productDTO) {
		// [1],[2]단계
		Connection conn=JDBCUtil.connect();
		ResultSet rs = null; // 결과 집합 객체
		// [3]단계
		PreparedStatement pstmt=null;
		
		if (conn == null) {
			System.err.println("DB 연결 실패");
			return false; // 또는 적절한 오류 처리를 수행
		}

		try {
			System.out.println("model.ProductDAO.insert 시작");
			//conn을 통하여 원하는 쿼리를 읽어올 준비를 한다
			if(productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_BOAR_INSERT")) {
				// 바다 낚시배 크롤링 insert
				System.out.println("model.ProductDAO.insert 바다 낚시배 시작");
				pstmt=conn.prepareStatement(PRODUCT_CRAWLING_SEA_BOAR_INSERT);

				// C에게서 받아온 값을 저장해줌
				// 상품명, 가격, 설명, 재고, 주소
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setString(3, productDTO.getProduct_details());// 설명
				pstmt.setInt(4, productDTO.getProduct_cnt());// 재고
				pstmt.setString(5, productDTO.getProduct_address());// 주소
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_SEA_FISHING_INSERT")) {
				// 바다 낚시터 크롤링
				System.out.println("model.ProductDAO.insert 바다 낚시터 시작");
				pstmt=conn.prepareStatement(PRODUCT_CRAWLING_SEA_FISHING_INSERT);	

				// 상품 명, 가격, 주소
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setString(3, productDTO.getProduct_address());// 주소
			}		
			else if(productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT")) {
				// 민물 낚시터
				System.out.println("model.ProductDAO.insert 민물 낚시터 시작");
				pstmt=conn.prepareStatement(PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT);	

				// 상품 명, 가격, 재고, 주소
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setInt(3, productDTO.getProduct_cnt());// 수량
				pstmt.setString(4, productDTO.getProduct_address());// 주소

			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT")) {
				// 민물 낚시카페
				System.out.println("model.ProductDAO.insert 민물 낚시카페 시작");
				pstmt=conn.prepareStatement(PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT);	

				// 상품 명, 가격, 재고, 주소
				pstmt.setString(1, productDTO.getProduct_name()); // 상품명
				pstmt.setInt(2, productDTO.getProduct_price()); // 가격
				pstmt.setString(3, productDTO.getProduct_address());// 주소

			}
			// 쿼리 실행
			int result = pstmt.executeUpdate();
			System.out.println("model.ProductDAO.insert  result : " + result);

			if (result > 0) {
				// 새로 생성된 PRODUCT_NUM 가져오기
				String selectQuery = "SELECT MAX(PRODUCT_NUM) FROM PRODUCT";
				pstmt = conn.prepareStatement(selectQuery);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					int newProductNum = rs.getInt(1);
					productDTO.setProduct_num(newProductNum); // 새로 생성된 PRODUCT_NUM 설정
					System.out.println("새로 생성된 PRODUCT_NUM: " + newProductNum);
				}
				System.out.println("model.ProductDAO.insert 행 변경 성공");
			} else {
				System.out.println("model.ProductDAO.insert 행 변경 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("SQL문 실패: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
			JDBCUtil.disconnect(conn, pstmt); 
			System.out.println("model.ProductDAO.insert 종료");
		
		return true; // 성공적으로 삽입 완료
	}


	public ArrayList<ProductDTO> selectAll(ProductDTO productDTO){ // 전체 출력
		ArrayList<ProductDTO> datas=new ArrayList<ProductDTO>();

		// [1],[2] 단계
		Connection conn = JDBCUtil.connect();

		// [3] 단계
		PreparedStatement pstmt=null;
		try {
			System.out.println("model.ProductDAO.selectAll 시작");


			if(productDTO.getProduct_condition().equals("PRODUCT_BY_ALL")) { // 상품 전체 출력 (최신순)
				System.out.println("model.ProductDAO.selectAll 상품 전체 목록 출력 (최신순) 시작");
				pstmt = conn.prepareStatement(SELECTALL);
				// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
				pstmt.setInt(1, productDTO.getProduct_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(2, productDTO.getProduct_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_BY_RATING")) { // 별점순으로 출력
				System.out.println("model.ProductDAO.selectAll 별점 높은 순 출력 시작");
				pstmt = conn.prepareStatement(SELECTALL_RATING);
				// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
				pstmt.setInt(1, productDTO.getProduct_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(2, productDTO.getProduct_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_BY_RESERVATIONS")) { // 결제많은순 출력
				System.out.println("model.ProductDAO.selectAll 결제 많은 순 출력 시작");
				pstmt = conn.prepareStatement(SELECTALL_RESERVATION);
				// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
				pstmt.setInt(1, productDTO.getProduct_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(2, productDTO.getProduct_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_BY_WISHLIST")) { // 찜많은순 출력
				System.out.println("model.ProductDAO.selectAll 찜 많은 순 출력 시작");
				pstmt = conn.prepareStatement(SELECTALL_WISHLIST);
				// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
				pstmt.setInt(1, productDTO.getProduct_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(2, productDTO.getProduct_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_BY_KEYWORD")) { // 상품명으로 검색 :  키워드(단어)로 검색
				System.out.println("model.ProductDAO.selectAll 상품명으로 검색하여 출력 시작");
				pstmt = conn.prepareStatement(SELECTALL_SEARCHKEYWORD);
				// 검색어 파라미터 1개 받아와야 함
				// 검색어를 1번 파라미터에 넣어준다.
				pstmt.setString(1, productDTO.getProduct_searchKeyword()); // 검색어
				// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
				pstmt.setInt(2, productDTO.getProduct_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(3, productDTO.getProduct_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_BY_FILTERING")) { // 장소(바다,민물)별, 유형(낚시배, 낚시터) 별 검색 (필터링 검색)
				System.out.println("model.ProductDAO.selectAll 필터링하여 출력 시작");
				pstmt = conn.prepareStatement(SELECTALL_FILTERING);
				// PRODUCT_LOCATION과 PRODUCT_CATEGORY 받아와야 함
				pstmt.setString(1, productDTO.getProduct_location()); // 상품 장소 (바다/민물)
				pstmt.setString(2, productDTO.getProduct_category()); // 상품 유형 (낚시배/낚시터/낚시카페/수상)
				// 페이지네이션 기능을 사용해야 하므로 페이지 번호를 받아와 파라미터에 넣어준다.
				pstmt.setInt(3, productDTO.getProduct_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
				pstmt.setInt(4, productDTO.getProduct_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
			}
			else{
				System.out.println("model.ProductDAO.selectAll 컨디션 실패");
			}

			// R(select)의 경우에는 executeQuery 실행하여 ResultSet 객체를 반환받는다.
			ResultSet rs = pstmt.executeQuery();
			// rs.next로 한 줄씩 읽어오는데, while문을 사용하여 끝까지 다 읽어온다.
			while(rs.next()) {
				System.out.println("model.ProductDAO.selectAll rs.next()실행");
				// 한 번에 객체에 담아 반환해야 하므로 DTO 객체 생성
				ProductDTO data = new ProductDTO();
				data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
				data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
				data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
				data.setProduct_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
				data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
				data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터,낚시카페, 수상)
				data.setProduct_avg_rating(rs.getDouble("RATING")); // 별점 평균
				data.setProduct_payment_cnt(rs.getInt("PAYMENT_COUNT")); // 결제 수
				data.setProduct_wishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 찜 수
				data.setProduct_file_name(rs.getString("FILE_NAME")); // 파일명
				data.setProduct_file_extension(rs.getString("FILE_EXTENSION")); // 파일 확장자
				data.setProduct_file_dir(rs.getString("FILE_DIR")); // 파일 경로

				// AL에 DTO 객체를 담아준다. (C에게 전달하기 위해)
				datas.add(data);
				System.out.println("model.ProductDAO.selectAll datas:" + datas);
			}

		} catch (SQLException e) {
			System.err.println("SQL문 실패..");
		}


		// 선택 상품 상세보기

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ProductDAO.selectAll 종료");
		return datas;
	}


	public ProductDTO selectOne(ProductDTO productDTO){// 한개 출력
		ProductDTO data=null;

		// [1],[2] 단계
		Connection conn = JDBCUtil.connect();
    

		// [3] 단계
		PreparedStatement pstmt=null;


		try {

			System.out.println("model.ProductDAO.selectOne 시작");
			// conn을 이용하여 SQL 쿼리를 받아올 준비를 한다.

			if(productDTO.getProduct_condition().equals("PRODUCT_BY_INFO")) { 
				// 상품 상세보기
				System.out.println("model.ProductDAO.selectOne 상품 상세보기 쿼리 읽기 시작");
				pstmt=conn.prepareStatement(SELECTONE);
				// 1번 파라미터에 PRODUCT_NUM 상품번호
				pstmt.setInt(1, productDTO.getProduct_num());
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_BY_CURRENT_STOCK")) { 
				// 사용자가 선택한 일자의 재고 보기
				System.out.println("model.ProductDAO.selectOne 선택일자 재고보기 쿼리 읽기 시작");
				pstmt=conn.prepareStatement(SELECTONE_CURRENT_STOCK);
				// 1번 파라미터에 RESERVATION_REGISTRATION_DATE 선택한 예약일
				pstmt.setDate(1, productDTO.getProduct_reservation_date());
				// 2번 파라미터에 PRODUCT_NUM 상품번호
				pstmt.setInt(2, productDTO.getProduct_num());
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_NUM_SELECT")) { 
				// 크롤링 select
				// 상품 번호
				System.out.println("model.ProductDAO.selectOne 크롤링 상품번호 쿼리 읽기 시작");
				pstmt=conn.prepareStatement(PRODUCT_NUM_SELECT);
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_SELECT")) { // 전체 출력
				// 크롤링 확인 전체 출력
				System.out.println("model.ProductDAO.selectOne 상품데이터 확인을 위한 읽기 시작");
				pstmt=conn.prepareStatement(PRODUCT_SELECT); 
			}
			else if(productDTO.getProduct_condition().equals("PRODUCT_PAGE_COUNT")) {
				// 페이지네이션에 사용하기 위해 전체 페이지 수 반환
				System.out.println("model.ProductDAO.selectOne 전체 상품 페이지 수 반환 쿼리 읽기 if문 시작");
				if(productDTO.getProduct_searchKeyword()!=null && !productDTO.getProduct_searchKeyword().isEmpty()) {
					System.out.println("model.ProductDAO.selectOne 키워드 검색 페이지 수 반환 쿼리 읽기 시작");
					// 키워드 검색
					// 만약 검색어가 null이 아니고, 빈 문자열도 아닐 때 (검색어가 있을 때)
					// 동적 쿼리를 사용하여 기본 + AND 쿼리 
					pstmt=conn.prepareStatement(PRODUCT_TOTAL_PAGE + " " +PRODUCT_SEARCH_PAGE);
					// 검색어를 찾기 위해 파라미터에 값을 넣어준다.
					pstmt.setString(1, productDTO.getProduct_searchKeyword()); // 검색어
				}
				else if(productDTO.getProduct_location() != null && !productDTO.getProduct_location().isEmpty()
					    || productDTO.getProduct_category() != null && !productDTO.getProduct_category().isEmpty()) {
					System.out.println("model.ProductDAO.selectOne 필터링 검색 페이지 수 반환 쿼리 읽기 시작");
					// 필터링 검색
					pstmt=conn.prepareStatement(PRODUCT_TOTAL_PAGE + " " +PRODUCT_FILTERING_PAGE);
					// 특정 조건을 검색하기 위해 파라미터에 값을 넣어준다.
					pstmt.setString(1, productDTO.getProduct_location()); // 상품 장소 (바다/민물)
					pstmt.setString(2, productDTO.getProduct_category()); // 상품 유형 (낚시배/낚시터/낚시카페/수상)
				}
				else {
					System.out.println("model.ProductDAO.selectOne 전체 페이지 수 반환 쿼리 읽기 시작");
					// 전체 검색
					pstmt=conn.prepareStatement(PRODUCT_TOTAL_PAGE);
				}
			}
			else {
				System.err.println("model.ProductDAO.selectOne 쿼리문 읽어오기 컨디션 실패");
			}


			// R(select)의 경우에는 executeQuery 실행하여 ResultSet 객체를 반환받는다.
			ResultSet rs = pstmt.executeQuery();
			// rs.next로 한 줄씩 읽어오는데, 1건만 반환하므로 if문을 사용하여 읽어온다.
			if(rs.next()) {
				data=new ProductDTO();
				System.out.println("model.ProductDAO.selectOne 한 줄씩 읽어오기 if문 시작");
				System.out.println("로그 컨디션"+productDTO.getProduct_condition());

				// 다시 컨디션별로 나눠준다.
				if(productDTO.getProduct_condition().equals("PRODUCT_BY_INFO")) { 
					// 상품 상세보기
					System.out.println("model.ProductDAO.selectOne 상품 상세보기(값 담기) 시작");
					data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
					data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
					data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
					data.setProduct_details(rs.getString("PRODUCT_DETAILS")); // 상품 설명
					data.setProduct_address(rs.getString("PRODUCT_ADDRESS")); // 상품 주소
					data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
					data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터)
					data.setProduct_avg_rating(rs.getDouble("RATING")); // 별점 평균
					data.setProduct_payment_cnt(rs.getInt("PAYMENT_COUNT")); // 결제 수
					data.setProduct_wishlist_cnt(rs.getInt("WISHLIST_COUNT")); // 찜 수
				}
				else if(productDTO.getProduct_condition().equals("PRODUCT_BY_CURRENT_STOCK")) { 
					// 사용자가 선택한 일자의 재고 보기
					System.out.println("model.ProductDAO.selectOne 선택일자 재고보기(값 담기) 시작");
					data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
					data.setProduct_cnt(rs.getInt("CURRENT_STOCK")); // 상품의 재고
				}
				else if(productDTO.getProduct_condition().equals("PRODUCT_NUM_SELECT")) {
					// 상품번호 보여주기 (크롤링)
					System.out.println("model.ProductDAO.selectOne 상품번호 출력(값 담기) 시작");
					data.setProduct_num(rs.getInt("MAX_NUM")); // 상품 번호
				}
				else if(productDTO.getProduct_condition().equals("PRODUCT_SELECT")) { // 전체 출력
					System.out.println("model.ProductDAO.selectOne 상품데이터 확인을 위한 추가 시작");
					data.setProduct_num(rs.getInt("PRODUCT_NUM")); // 상품 번호
					data.setProduct_name(rs.getString("PRODUCT_NAME")); // 상품명
					data.setProduct_price(rs.getInt("PRODUCT_PRICE")); // 상품 가격
					data.setProduct_details(rs.getString("PRODUCT_DETAILS")); // 상품 설명
					data.setProduct_location(rs.getString("PRODUCT_LOCATION")); // 상품 장소 (바다,민물)
					data.setProduct_category(rs.getString("PRODUCT_CATEGORY")); // 상품 유형 (낚시배, 낚시터)

				}
				else if(productDTO.getProduct_condition().equals("PRODUCT_PAGE_COUNT")) {
					// 상품 테이블의 전체 개수 출력 (페이지네이션)
					System.out.println("model.ProductDAO.selectOne 상품 페이지 수 출력 (값 담기) 시작");
					data.setProduct_total_page(rs.getInt("PRODUCT_TOTAL_PAGE")); // 상품 개수
				}
				else {
					System.err.println("model.ProductDAO.selectOne 객체에 담아주기 컨디션 실패");
				}

				System.out.println("model.ProductDAO.selectOne data : " + data);
			}
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
		}

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ProductDAO.selectOne 종료");

		return data;
	}



	// 기능 미구현으로 private 처리
	private boolean update(ProductDTO productDTO){	// 입력

		return false;
	}
	// 기능 미구현으로 private 처리
	private boolean delete(ProductDTO productDTO){	// 입력

		return false;
	}


}
