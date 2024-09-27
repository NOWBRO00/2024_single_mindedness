package model.file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;

public class FileDAO { // 이미지 파일

	// 쿼리문
	// 게시판 사진 저장
	private final String BOARD_FILE_INSERT="INSERT INTO IMAGEFILE (FILE_NUM,FILE_ORIGINAL_NAME,FILE_NAME,FILE_EXTENSION,FILE_DIR,BOARD_ITEM_NUM)  \r\n"
			+ "VALUES (COALESCE((SELECT MAX(FILE_NUM) FROM IMAGEFILE),0)+1,?,?,?,?,?)";
	// 상품 사진 저장
	private final String PRODUCT_FILE_INSERT = "INSERT INTO IMAGEFILE (FILE_NUM,FILE_ORIGINAL_NAME,FILE_NAME,FILE_EXTENSION,FILE_DIR,PRODUCT_ITEM_NUM)  \r\n"
			+ "VALUES (COALESCE((SELECT MAX(FILE_NUM) FROM IMAGEFILE),0)+1,?,?,?,?,?)";

	// 사진 변경
	private final String FILE_UPDATE="UPDATE IMAGEFILE SET FILE_NAME=?,FILE_EXTENSION=?,FILE_DIR=? WHERE FILE_NUM=?";
	// 사진 삭제
	private final String FILE_DELETE="DELETE FROM IMAGEFILE WHERE FILE_NUM=?";

	// 게시판 사진 전체 출력
	private final String BOARD_FILE_SELECTALL = "SELECT FILE_NUM, FILE_ORIGINAL_NAME, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM IMAGEFILE WHERE BOARD_ITEM_NUM=? ORDER BY FILE_NUM";
	// 상품 사진 전체 출력
	private final String PRODUCT_FILE_SELECTALL = "SELECT FILE_NUM, FILE_ORIGINAL_NAME, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
			+ "FROM IMAGEFILE WHERE PRODUCT_ITEM_NUM=? ORDER BY FILE_NUM";
	// 사진 한개 출력
	// 24.09.05 사진 1개 출력은 각각 상품테이블과 게시판 테이블에서 JOIN으로 기능 구현으로 ImageFile에서는 미사용
	//	private final String FILE_SELECTONE="SELECT FILE_NUM, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
	//			+ "FROM (SELECT FILE_NUM, FILE_NAME, FILE_EXTENSION, FILE_DIR, ITEM_NUM, FILE_CONDITION FROM IMAGEFILE WHERE ITEM_NUM=? AND FILE_CONDITION=? ORDER BY FILE_NUM)\r\n"
	//			+ "WHERE ROWNUM=1";

	// 크롤링 insert
	private final String FILE_BOARD_CRAWLING_INSERT="INSERT INTO IMAGEFILE (FILE_NUM,FILE_ORIGINAL_NAME,FILE_NAME,FILE_EXTENSION,FILE_DIR,BOARD_ITEM_NUM) \r\n"
			+ "VALUES (COALESCE((SELECT MAX(FILE_NUM) FROM IMAGEFILE),0)+1,?,'PHOTO','jpg',?,?)";
	private final String FILE_PRODUCT_CRAWLING_INSERT="INSERT INTO IMAGEFILE (FILE_NUM,FILE_ORIGINAL_NAME,FILE_NAME,FILE_EXTENSION,FILE_DIR,PRODUCT_ITEM_NUM) \r\n"
			+ "VALUES (COALESCE((SELECT MAX(FILE_NUM) FROM IMAGEFILE),0)+1,?,'PHOTO','jpg',?,?)";


	public boolean insert(FileDTO imageFileDTO){	// 생성

		// [1],[2]단계
		Connection conn=JDBCUtil.connect();
		if (conn == null) {
			System.err.println("DB 연결 실패");
			return false; // 또는 적절한 오류 처리를 수행
		}
		// [3]단계
		// 사진 저장
		PreparedStatement pstmt=null;
		// 글 상세페이지 출력

		try {

			if(imageFileDTO.getFile_condition().equals("BOARD_FILE_INSERT")) {
				// 게시판 사진 insert
				System.out.println("model.ImageFileDAO.insert BOARD_FILE_INSERT 시작");
				//conn을 통하여 원하는 쿼리를 읽어올 준비를 한다
				pstmt=conn.prepareStatement(BOARD_FILE_INSERT);

				// C에게서 받아온 값을 저장해줌
				pstmt.setString(1, imageFileDTO.getFile_original_name()); // 원본 파일명
				pstmt.setString(2, imageFileDTO.getFile_name()); // 저장되는 파일명
				pstmt.setString(3, imageFileDTO.getFile_extension()); // 파일 확장자
				pstmt.setString(4, imageFileDTO.getFile_dir()); //파일 경로
				pstmt.setInt(5, imageFileDTO.getFile_board_num()); // 게시판 글 번호
			}
			else if(imageFileDTO.getFile_condition().equals("PRODUCT_FILE_INSERT")) {
				// 상품 사진 insert
				System.out.println("model.ImageFileDAO.insert PRODUCT_FILE_INSERT 시작");
				//conn을 통하여 원하는 쿼리를 읽어올 준비를 한다
				pstmt=conn.prepareStatement(PRODUCT_FILE_INSERT);

				// C에게서 받아온 값을 저장해줌
				pstmt.setString(1, imageFileDTO.getFile_original_name()); // 원본 파일명
				pstmt.setString(2, imageFileDTO.getFile_name()); // 저장되는 파일명
				pstmt.setString(3, imageFileDTO.getFile_extension()); // 파일 확장자
				pstmt.setString(4, imageFileDTO.getFile_dir()); //파일 경로
				pstmt.setInt(5, imageFileDTO.getFile_product_num()); // 상품 글 번호
			}
			else if(imageFileDTO.getFile_condition().equals("FILE_BOARD_CRAWLING_INSERT")) {
				// 게시판 크롤링 insert
				System.out.println("model.ImageFileDAO.insert FILE_BOARD_CRAWLING_INSERT 시작");
				pstmt=conn.prepareStatement(FILE_BOARD_CRAWLING_INSERT);

				// C에게서 받아온 값을 저장해줌
				pstmt.setString(1, imageFileDTO.getFile_original_name());// 파일 이름
				pstmt.setString(2, imageFileDTO.getFile_dir()); //파일 경로
				pstmt.setInt(3, imageFileDTO.getFile_board_num()); // 게시판 글 번호

			}
			else if(imageFileDTO.getFile_condition().equals("FILE_PRODUCT_CRAWLING_INSERT")) {
				// 상품 크롤링 insert
				System.out.println("model.ImageFileDAO.insert FILE_PRODUCT_CRAWLING_INSERT 시작");
				//conn을 통하여 원하는 쿼리를 읽어올 준비를 한다
				pstmt=conn.prepareStatement(FILE_PRODUCT_CRAWLING_INSERT);

				// C에게서 받아온 값을 저장해줌
				pstmt.setString(1, imageFileDTO.getFile_original_name());// 파일 이름
				pstmt.setString(2, imageFileDTO.getFile_dir()); //파일 경로
				pstmt.setInt(3, imageFileDTO.getFile_product_num()); // 상품 글 번호
			}
			else {
				System.err.println("컨디션 실패");
			}

			// CUD : executeUpdate - 변경이 된 행 수 반환
			int result = pstmt.executeUpdate();
			System.out.println("model.ImageFileDAO.insert result : " + result);

			if(result<=0) { // 만약 변경이 된 행 수가 0보다 작거나 같다면
				System.out.println("model.ImageFileDAO.insert 행 변경 실패");
				// false 반환
				return false;
			}
			System.out.println("model.ImageFileDAO.insert 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			e.printStackTrace();
			// 실패시에도 false 반환해주기
			return false;
		}

		// [4]단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ImageFileDAO.insert 종료");
		return true;
	}



	public boolean update(FileDTO imageFileDTO){	// 업데이트

		// [1],[2] 단계
		Connection conn=JDBCUtil.connect();

		// [3] 단계
		// 사진 변경
		PreparedStatement pstmt=null;

		try {
			System.out.println("model.ImageFileDTO.update 시작");
			//conn을 이용해 SQL 쿼리를 읽어올 준비를 함
			pstmt=conn.prepareStatement(FILE_UPDATE);

			// C에게 받은 값을 저장해준다.
			pstmt.setString(1, imageFileDTO.getFile_name()); // 파일명
			pstmt.setString(2, imageFileDTO.getFile_extension()); //파일 확장자명
			pstmt.setString(3, imageFileDTO.getFile_dir());//파일 경로
			pstmt.setInt(4, imageFileDTO.getFile_num()); //파일 번호

			// CUD : executeUpdate 쿼리로 인하여 변경이 된 행 수 반환
			int result = pstmt.executeUpdate();
			System.out.println("model.ImageFileDTO.update result : "+result);

			if(result<=0) { // 만약 변경된 행 수가 없다면
				System.out.println("model.ImageFileDTO.update 행 변경 실패");
				// 변경 실패한 것으로 false 반환
				return false;
			}
			System.out.println("model.ImageFileDTO.update 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
		}

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ImageFileDTO.update 종료");
		return true;
	}



	public boolean delete(FileDTO imageFileDTO){	// 삭제

		// [1],[2] 단계
		Connection conn=JDBCUtil.connect();

		// [3] 단계
		// 사진 삭제
		PreparedStatement pstmt=null;

		try {
			System.out.println("model.ImageFileDTO.delete 시작");
			// conn 객체를 사용하여 쿼리를 받아올 준비를 한다
			pstmt=conn.prepareStatement(FILE_DELETE);
			pstmt.setInt(1, imageFileDTO.getFile_num());//파일번호

			// CUD : executeUpdate 쿼리로 인하여 변경이 된 행 수 반환
			int result = pstmt.executeUpdate();
			System.out.println("model.ImageFileDTO.delete result : "+result);

			if(result<=0) { // 만약 변경된 행 수가 없다면
				System.out.println("model.ImageFileDTO.delete 행 변경 실패");
				// 변경 실패한 것으로 false 반환
				return false;
			}
			System.out.println("model.ImageFileDTO.delete 행 변경 성공");
		} catch (SQLException e) {
			System.err.println("SQL문 실패");
			return false;
		}

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ImageFileDTO.delete 종료");

		return true;
	}

	public ArrayList<FileDTO> selectAll(FileDTO imageFileDTO){ // 전체 출력
		ArrayList<FileDTO> datas=new ArrayList<FileDTO>();


		// [1],[2] 단계
		Connection conn=JDBCUtil.connect();

		// [3] 단계
		// 게시글 선택시 해당 글 사진 전체 출력
		PreparedStatement pstmt=null;

		try {
			System.out.println("model.ImageFileDTO.selectAll 시작");
			
			if(imageFileDTO.getFile_condition().equals("BOARD_FILE_SELECTALL")) {
				// 게시판 사진 전체 출력
				System.out.println("model.ImageFileDTO.selectAll BOARD_FILE_SELECTALL 시작");
				// conn을 통하여 SQL 쿼리를 읽어올 준비를 한다
				pstmt=conn.prepareStatement(BOARD_FILE_SELECTALL);
				// 찾아오기 위한 값을 파라미터에 넣어준다
				pstmt.setInt(1, imageFileDTO.getFile_board_num()); // 게시판 글 번호

			}
			else if(imageFileDTO.getFile_condition().equals("PRODUCT_FILE_SELECTALL")) {
				// 상품 사진 전체 출력
				System.out.println("model.ImageFileDTO.selectAll PRODUCT_FILE_SELECTALL 시작");
				// conn을 통하여 SQL 쿼리를 읽어올 준비를 한다
				pstmt=conn.prepareStatement(PRODUCT_FILE_SELECTALL);
				// 찾아오기 위한 값을 파라미터에 넣어준다
				pstmt.setInt(1, imageFileDTO.getFile_product_num()); // 상품 글 번호
			}
			
			// select 는 executeQuery >> resultSet 객체 반환
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				System.out.println("model.ImageFileDTO.selectAll resultSet 객체 읽어오기");
				// resultset 객체를 한 줄씩 끝까지 읽어오며 객체에 저장
				// ArrayList에 담아서 전달해줘야 하므로, 반복하며 저장할 객체 생성
				FileDTO data=new FileDTO();

				data.setFile_num(rs.getInt("FILE_NUM")); // 파일 번호
				data.setFile_original_name(rs.getString("FILE_ORIGINAL_NAME")); // 파일 원본명
				data.setFile_name(rs.getString("FILE_NAME")); // 파일 저장되는 이름
				data.setFile_extension(rs.getString("FILE_EXTENSION")); //파일 확장자
				data.setFile_dir(rs.getString("FILE_DIR")); // 파일 경로

				// 객체를 AL에 저장해주기 (C에게 전달용)
				datas.add(data);
				System.out.println("model.ImageFileDTO.selectAll datas : " + datas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL문 실패");
		}

		// [4] 단계
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("model.ImageFileDTO.selectAll 종료");
		return datas;
	}






	// 24.09.05 사진 1개 출력은 각각 상품테이블과 게시판 테이블에서 JOIN으로 기능 구현
	// 따라서 기능 미사용 : private 처리
	private FileDTO selectOne(FileDTO imageFileDTO){// 한명 출력
		FileDTO data=null;
		//
		//
		//		// [1],[2] 단계
		//		Connection conn=JDBCUtil.connect();
		//
		//		// [3] 단계
		//		// 가장 최근 사진 보여주기
		//		PreparedStatement pstmt=null;
		//
		//		try {
		//			System.out.println("model.ImageFileDTO.selectOne 시작");
		//			// conn을 통하여 SQL 쿼리를 읽어올 준비를 한다
		//			pstmt=conn.prepareStatement(FILE_SELECTONE);
		//
		//			// 찾아오기 위한 값을 파라미터에 넣어준다
		//			pstmt.setInt(1, imageFileDTO.getFile_item_Num()); // 글 or 상품 번호
		//			pstmt.setInt(2, imageFileDTO.getFile_condition()); // 글 or 상품 구분, 0은 글 1은 상품
		//
		//			// select 는 executeQuery >> resultSet 객체 반환
		//			ResultSet rs = pstmt.executeQuery();
		//
		//			while(rs.next()) {
		//				System.out.println("model.ImageFileDTO.selectOne resultSet 객체 읽어오기");
		//				// resultset 객체를 한 줄씩 끝까지 읽어오며 객체에 저장
		//
		//				data.setFile_num(rs.getInt("FILE_NUM")); // 파일 번호
		//				data.setFile_name(rs.getString("FILE_NAME")); // 파일 이름
		//				data.setFile_extension(rs.getString("FILE_EXTENSION")); //파일 확장자
		//				data.setFile_dir(rs.getString("FILE_DIR")); // 파일 경로
		//
		//				System.out.println("model.ImageFileDTO.selectOne data : " + data);
		//			}
		//
		//		} catch (SQLException e) {
		//			System.err.println("SQL문 실패");
		//		}
		//
		//		// [4] 단계
		//		JDBCUtil.disconnect(conn, pstmt);
		//		System.out.println("model.ImageFileDTO.selectOne 종료");

		return data;
	}


}