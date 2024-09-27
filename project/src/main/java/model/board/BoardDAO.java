package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;



public class BoardDAO { // 게시판

   // SQL문 선언하는 공간
   // 게시글 작성
   private final String BOARD_INSERT="INSERT INTO BOARD (BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_WRITER_ID) VALUES(COALESCE((SELECT MAX(BOARD_NUM) FROM BOARD), 0) + 1, ?,?,?)"; 
   // 게시글 수정
   private final String BOARD_UPDATE="UPDATE BOARD SET BOARD_TITLE=COALESCE(?,BOARD_TITLE), BOARD_CONTENT=COALESCE(?,BOARD_CONTENT) WHERE BOARD_NUM=?";
   // 게시글 삭제
   private final String BOARD_DELETE="DELETE FROM BOARD WHERE BOARD_NUM=?";


   //게시판 전체 출력
   private final String BOARD_SELECTALL="SELECT BOARD_NUM, BOARD_TITLE, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR \r\n"
         + "FROM (SELECT BOARD_NUM, BOARD_TITLE, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
         + "         ROW_NUMBER() OVER(ORDER BY BOARD_NUM DESC) AS ROW_NUM\r\n"
         + "         FROM BOARD_INFO_VIEW)\r\n"
         + "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";
   // 제목+내용으로 검색
   private final String BOARD_SELECTALL_SEARCH="SELECT BOARD_NUM, BOARD_TITLE, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR \r\n"
         + "FROM (SELECT BOARD_NUM, BOARD_TITLE,BOARD_CONTENT, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
         + "         ROW_NUMBER() OVER(ORDER BY BOARD_NUM DESC) AS ROW_NUM\r\n"
         + "         FROM BOARD_INFO_VIEW\r\n"
         + "         WHERE BOARD_TITLE LIKE '%'||?||'%' OR BOARD_CONTENT LIKE '%'||?||'%')\r\n"
         + "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9"; 
   // 좋아요 순으로 검색
   private final String BOARD_SELECTALL_LIKE="SELECT BOARD_NUM, BOARD_TITLE, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR\r\n"
         + "FROM(SELECT BOARD_NUM, BOARD_TITLE, BOARD_REGISTRATION_DATE, LIKE_COUNT, FILE_NAME, FILE_EXTENSION, FILE_DIR,\r\n"
         + "         ROW_NUMBER() OVER(ORDER BY LIKE_COUNT DESC) AS ROW_NUM\r\n"
         + "         FROM BOARD_INFO_VIEW)\r\n"
         + "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9"; 
   // 내 글 전체보기
      private final String BOARD_SELECTALL_MYBOARD="SELECT MB.BOARD_NUM, MB.BOARD_TITLE, MB.BOARD_REGISTRATION_DATE, MB.MEMBER_NICKNAME,MB.LIKE_COUNT, MB.FILE_NAME, MB.FILE_EXTENSION, MB.FILE_DIR\r\n"
            + "FROM (SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_REGISTRATION_DATE, M.MEMBER_NICKNAME, (SELECT COUNT(G.LIKE_BOARD_NUM) FROM GOODLIKE G WHERE G.LIKE_BOARD_NUM=B.BOARD_NUM) AS LIKE_COUNT, I.FILE_NAME, I.FILE_EXTENSION, I.FILE_DIR,\r\n"
            + "ROW_NUMBER() OVER(ORDER BY BOARD_NUM DESC) AS ROW_NUM\r\n"
            + "FROM BOARD B\r\n"
            + "JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID\r\n"
            + "LEFT JOIN (SELECT FILE_NAME, FILE_EXTENSION, FILE_DIR, BOARD_ITEM_NUM FROM (SELECT FILE_NAME, FILE_EXTENSION, FILE_DIR, BOARD_ITEM_NUM, ROW_NUMBER() OVER(PARTITION BY BOARD_ITEM_NUM ORDER BY FILE_NUM)AS RN FROM IMAGEFILE)WHERE RN=1)I ON B.BOARD_NUM = I.BOARD_ITEM_NUM\r\n"
            + "WHERE B.BOARD_WRITER_ID = ?)MB\r\n"
            + "WHERE ROW_NUM BETWEEN (COALESCE(?,1)-1)*9+1 AND COALESCE(?,1)*9";



   // 글 상세페이지 출력
   private final String BOARD_SELECTONE="SELECT B.BOARD_NUM, B.BOARD_TITLE, B.BOARD_CONTENT, B.BOARD_REGISTRATION_DATE, COALESCE(B.BOARD_WRITER_ID,'탈퇴한 회원입니다') AS MEMBER_ID, COALESCE(M.MEMBER_NICKNAME, '탈퇴한 회원입니다.') AS MEMBER_NICKNAME, COALESCE(ML.LV_COUNT,1) AS MEMBER_LEVEL,\r\n"
         + "(SELECT COUNT(LIKE_BOARD_NUM) FROM GOODLIKE G WHERE G.LIKE_BOARD_NUM=B.BOARD_NUM) AS LIKE_COUNT\r\n"
         + "FROM BOARD B\r\n"
         + "LEFT JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID\r\n"
         + "LEFT JOIN (SELECT B2.BOARD_WRITER_ID,COUNT(B2.BOARD_WRITER_ID) AS LV_COUNT FROM BOARD B2 GROUP BY B2.BOARD_WRITER_ID)ML ON M.MEMBER_ID=ML.BOARD_WRITER_ID\r\n"
         + "WHERE B.BOARD_NUM=?"; 
   // 가장 마지막 글 번호 출력
   private final String BOARD_SELECTONE_LAST_NUM="SELECT BOARD_NUM FROM (SELECT BOARD_NUM FROM BOARD ORDER BY BOARD_NUM DESC) WHERE ROWNUM=1";
   // 전체 페이지 수 출력 (기본)
   private final String BOARD_TOTAL_PAGE = "SELECT CEIL(COALESCE(COUNT(BOARD_NUM),0)/9.0)AS BOARD_TOTAL_PAGE FROM BOARD WHERE 1=1";
   // 전체 페이지 수 출력 (키워드 검색 페이지 수)
   private final String BOARD_SEARCH_PAGE = "AND BOARD_TITLE LIKE '%'||?||'%' OR BOARD_CONTENT LIKE '%'||?||'%'";
   // 전체 페이지 수 출력 (내가 쓴 글 페이지 수)
   private final String BOARD_MYBOARD_PAGE = "AND BOARD_WRITER_ID = ?";



   // 크롤링 insert
   private final String BOARD_CRAWLING_INSERT="INSERT INTO BOARD (BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_WRITER_ID) VALUES((SELECT NVL(MAX(BOARD_NUM),0)+1 FROM BOARD),?,?,?)"; 
   // 크롤링 select
   // 게시판 pk 출력
   private final String BOARD_NUM_SELECTONE="SELECT MAX(BOARD_NUM) AS MAX_NUM FROM BOARD";
   // 게시판 전체 출력
   private final String BOARD_SELECT="SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER_ID, BOARD_REGISTRATION_DATE FROM BOARD";



   public boolean insert(BoardDTO boardDTO){   // 입력(생성)

      // [1],[2] 단계
      Connection conn=JDBCUtil.connect();

      // [3] 단계
      // SQL문 쿼리를 실행시키기 위한 준비를 하는 객체를 선언한다.
      PreparedStatement pstmt = null;
      ResultSet rs = null; // 결과 집합 객체

      try {
         if(boardDTO.getBoard_condition().equals("BOARD_INSERT")) {
            System.out.println("model.BoardDAO.insert 시작");
            // 글 작성
            // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다.
            pstmt=conn.prepareStatement(BOARD_INSERT);
            // pstmt에 set으로 순서대로 값을 저장해준다.
            // pk값은 사용자가 지정하는 것이 아니기 때문에 입력하지 않고,
            // 글 작성일은 sysdate로 오늘 날짜가 들어가도록 설정함.
            pstmt.setString(1, boardDTO.getBoard_title()); // 글 제목 
            pstmt.setString(2, boardDTO.getBoard_content()); // 글 내용
            pstmt.setString(3, boardDTO.getBoard_writer_id());  // 글 작성자
            // CUD 이므로 executeUpdate 진행하고,
            // 변경이 된 행 수를 반환하므로 int 타입의 변수에 저장해준다.
         }
         else if(boardDTO.getBoard_condition().equals("BOARD_CRAWLING_INSERT")) {
            pstmt=conn.prepareStatement(BOARD_CRAWLING_INSERT);   
            pstmt.setString(1, boardDTO.getBoard_title()); // 글 제목 
            pstmt.setString(2, boardDTO.getBoard_content()); // 글 내용
            pstmt.setString(3, boardDTO.getBoard_writer_id());  // 글 작성자
         }


         int result = pstmt.executeUpdate();
         System.out.println("model.BoardDAO.insert result : " + result);

         if (result > 0) { // 데이터가 있을경우
            // 새로 생성된 BOARD_NUM 가져오기
            String selectQuery = "SELECT MAX(BOARD_NUM) FROM BOARD";
            pstmt = conn.prepareStatement(selectQuery);
            rs = pstmt.executeQuery();

            if (rs.next()) {
               int newBoardNum = rs.getInt(1);
               boardDTO.setBoard_num(newBoardNum); // 새로 생성된 BOARD_NUM 설정
               System.out.println("새로 생성된 BOARD_NUM: " + newBoardNum);
            }
            System.out.println("model.BoardDAO.insert 행 변경 성공");
         } else {
            System.out.println("model.BoardDAO.insert 행 변경 실패");
            return false; // 행 변경 실패 시 false 반환
         }

      } catch (SQLException e) {
         System.err.println("model.BoardDAO.insert SQL문 실패: " + e.getMessage());
         e.printStackTrace();
         return false; // 예외 발생 시 false 반환
      } finally {
         // [4]단계 자원 해제
         JDBCUtil.disconnect(conn, pstmt); // ResultSet도 닫기
         System.out.println("model.BoardDAO.insert 종료");
      }

      return true; // 성공적으로 삽입 완료
   }



   public boolean update(BoardDTO boardDTO){   // 업데이트

      // [1],[2] 단계
      Connection conn=JDBCUtil.connect();

      // [3] 단계
      // SQL문 쿼리를 실행시키기 위한 준비를 하는 객체를 선언한다.
      PreparedStatement pstmt = null;

      try {
         System.out.println("model.BoardDAO.update 시작");
         // 글 수정
         // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다.
         pstmt=conn.prepareStatement(BOARD_UPDATE);
         //.set으로 글에서 변경이 되는 제목/내용/글번호(글번호는 찾기 위함)를 저장해준다.
         pstmt.setString(1, boardDTO.getBoard_title()); // 글 제목
         pstmt.setString(2, boardDTO.getBoard_content()); // 글 내용
         pstmt.setInt(3, boardDTO.getBoard_num()); // 글 번호

         // CUD 이므로 executeUpdate 진행하고,
         // 변경이 된 행 수를 반환하므로 int 타입의 변수에 저장해준다.
         int result = pstmt.executeUpdate();
         System.out.println("model.BoardDAO.update result : " + result);

         if(result<=0) {
            System.out.println("model.BoardDAO.update 행 변경 실패");
            // 만약 반환된 수가 0보다 작으면 변경된 행이 없다는 뜻이므로 false 반환
            return false;
         }
         System.out.println("model.BoardDAO.update 행 변경 성공");
      } catch (SQLException e) {
         System.err.println("SQL문 실패");
         return false;
      }

      // [4] 단계 연결해제
      JDBCUtil.disconnect(conn, pstmt);
      System.out.println("model.BoardDAO.update 종료");

      return true;

   }


   public boolean delete(BoardDTO boardDTO){   // 삭제

      // [1],[2] 단계
      Connection conn=JDBCUtil.connect();

      // [3] 단계
      // SQL문 쿼리를 실행시키기 위한 준비를 하는 객체를 선언한다.
      PreparedStatement pstmt = null;

      try {
         System.out.println("model.BoardDAO.delete 시작");
         // 글 삭제
         // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다.
         pstmt=conn.prepareStatement(BOARD_DELETE);
         pstmt.setInt(1, boardDTO.getBoard_num()); // 글 번호

         // CUD 이므로 executeUpdate 진행하고,
         // 변경이 된 행 수를 반환하므로 int 타입의 변수에 저장해준다.
         int result = pstmt.executeUpdate();
         System.out.println("model.BoardDAO.delete result : " + result);

         if(result<=0) {
            System.out.println("model.BoardDAO.delete 행 변경 실패");
            // 만약 반환된 수가 0보다 작으면 변경된 행이 없다는 뜻이므로 false 반환
            return false;
         }
         System.out.println("model.BoardDAO.delete 행 변경 성공");
      } catch (SQLException e) {
         System.err.println("SQL문 실패");
         // 실패도 정상적으로 반영되지 않은 것이므로 false 반환
         return false;
      }

      // [4] 단계 연결해제
      JDBCUtil.disconnect(conn, pstmt);
      System.out.println("model.BoardDAO.delete 종료");

      return true;

   }

     public ArrayList<BoardDTO> selectAll(BoardDTO boardDTO){ // 전체 출력

         // C에게 반환하기 위한 ArrayList
         ArrayList<BoardDTO> datas=new ArrayList<BoardDTO>();

         // [1],[2] 단계
         Connection conn=JDBCUtil.connect();

         // [3] 단계
         // SQL문 쿼리를 실행시키기 위한 준비를 하는 객체를 선언한다.
         PreparedStatement pstmt = null;

         try {

            System.out.println("model.BoardDAO.selectAll 시작");

            // 글 전체목록 출력
            if(boardDTO.getBoard_condition().equals("BOARD_ALL")) {
               System.out.println("model.BoardDAO.selectAll_글 전체 출력 시작");
               // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다.
               pstmt=conn.prepareStatement(BOARD_SELECTALL);
               // 페이지네이션 
               pstmt.setInt(1, boardDTO.getBoard_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
               pstmt.setInt(2,  boardDTO.getBoard_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수

            }
            // 글 제목/내용으로 출력
            else if(boardDTO.getBoard_condition().equals("BOARD_CONTENTE")) {
               System.out.println("model.BoardDAO.selectAll_글 제목+내용 검색 시작");
               // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다.
               pstmt=conn.prepareStatement(BOARD_SELECTALL_SEARCH);
               // 제목 + 내용 검색이기 때문에 둘 중 한 곳에만 검색하는 값이 있어도 데이터를 보여줌
               // 따라서 1, 2번 파라미터에 인덱스 SearchKeyword() 값을 담아준다. (제목 / 내용)
               pstmt.setString(1, boardDTO.getBoard_searchKeyword());
               pstmt.setString(2, boardDTO.getBoard_searchKeyword());
               // 페이지네이션 
               pstmt.setInt(3, boardDTO.getBoard_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
               pstmt.setInt(4,  boardDTO.getBoard_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
            }
            // 좋아요 많은 순으로 출력
            else if(boardDTO.getBoard_condition().equals("BOARD_LIKE")) {
               System.out.println("model.BoardDAO.selectAll_글 좋아요 순 시작");
               // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다.
               pstmt=conn.prepareStatement(BOARD_SELECTALL_LIKE);
               // 페이지네이션 
               pstmt.setInt(1, boardDTO.getBoard_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
               pstmt.setInt(2,  boardDTO.getBoard_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
            }
            // 내가 쓴 글 전체 목록 출력
            else if(boardDTO.getBoard_condition().equals("MYBOARD_LIST")){
               System.out.println("model.BoardDAO.selectAll_내 글 전체 출력 시작");
               // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다
               pstmt=conn.prepareStatement(BOARD_SELECTALL_MYBOARD);
               // mid를 반환받기 때문에 1번 파라미터에 mid 값을 넣어준다.
               pstmt.setString(1, boardDTO.getBoard_writer_id()); // 작성자 id
               // 페이지네이션 
               pstmt.setInt(2, boardDTO.getBoard_page_num()); // 페이지 번호, 첫 데이터 계산 (페이지번호-1)*한 페이지에 나오는 데이터 수+1
               pstmt.setInt(3,  boardDTO.getBoard_page_num()); // 페이지 번호, 마지막 데이터 계산  페이지번호 *한 페이지에 나오는 데이터 수
            }     

            // R의 경우에는 executeQuery
            // 이는 ResultSet 객체를 반환하여, 쿼리를 한줄씩 읽어올 수 있도록 해준다.
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()) {
               System.out.println("model.BoardDAO.selectAll 쿼리문 읽어오기");
               // 반복문을 사용하여, next() 메서드 사용해 한 줄씩 읽어온다.
               // 여러개의 내용을 저장해서 한번에 보내줘야 하기 때문에, DTO타입 new로 생성해준다.
               BoardDTO data=new BoardDTO();
               data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
               data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목

               data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
               data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 글 좋아요 수
               data.setBoard_file_name(rs.getString("FILE_NAME")); // 파일 이름
               data.setBoard_file_extension(rs.getString("FILE_EXTENSION")); // 파일 확장자
               data.setBoard_file_dir(rs.getString("FILE_DIR")); // 파일 경로

               // 내 글 전체보기에만 작성자 닉네임이 보인다.
               // 읽어오는 중, condition이 내 글 전체보기 키워드라면
               // if문을 통하여 작성자 받아오기
               if(boardDTO.getBoard_condition().equals("MYBOARD_LIST")) {
                  System.out.println("model.BoardDAO.selectAll 내 글 전체보기_작성자 쿼리 if문");
                  data.setBoard_writer_id(rs.getString("MEMBER_NICKNAME")); // 회원 닉네임
               }

               System.out.println("model.BoardDAO.selectAll data : " + data);
               //ArrayList에 값 저장
               datas.add(data);
            }
         } catch (SQLException e) {
        	 e.printStackTrace();
            System.err.println("SQL문 실패");
         }

         // [4] 단계 연결해제
         JDBCUtil.disconnect(conn, pstmt);
         System.out.println("model.BoardDAO.selectAll 종료");

         return datas;
      }



   public BoardDTO selectOne(BoardDTO boardDTO){ // 한개 출력
      BoardDTO data=null;

      // [1],[2] 단계
      Connection conn=JDBCUtil.connect();

      // [3] 단계
      // SQL문 쿼리를 실행시키기 위한 준비를 하는 객체를 선언한다.
      PreparedStatement pstmt = null;

      // conn을 이용하여 SQL문 쿼리를 읽어올 준비를 한다.
      try {
         System.out.println("model.BoardDAO.selectOne 시작");


         // 글 상세페이지 출력
         if(boardDTO.getBoard_condition().equals("BOARD_SELECT_ONE")) {
            System.out.println("model.BoardDAO.selectOne 글 상세페이지 출력 쿼리 읽어오기 시작");
            pstmt=conn.prepareStatement(BOARD_SELECTONE);
            // 특정 정보를 조회할 때 필요하기 때문에, 
            // 1번 파라미터 인덱스에 get글번호를 담아준다.
            pstmt.setInt(1, boardDTO.getBoard_num());
         }
         // 가장 최근 게시글 번호를 받아오기 (파일에 저장하기 위해서)
         // 글번호만 있으면 됨
         else if(boardDTO.getBoard_condition().equals("LAST_BOARD_NUM")) {
            System.out.println("model.BoardDAO.selectOne 최근 글 번호 1개만 출력 쿼리 읽어오기 시작");
            pstmt=conn.prepareStatement(BOARD_SELECTONE_LAST_NUM);
         }
         // 크롤링 select
         else if(boardDTO.getBoard_condition().equals("BOARD_NUM_SELECTONE")) {
            //BOARD_NUM_SELECTONE
            System.out.println("model.BoardDAO.selectOne 글 번호 반환하기 쿼리 읽어오기  시작");
            pstmt=conn.prepareStatement(BOARD_NUM_SELECTONE);
           // pstmt.setInt(1, boardDTO.getBoard_num()); // 글 번호
         }
         else if(boardDTO.getBoard_condition().equals("BOARD_SELECT")) {
        	 pstmt=conn.prepareStatement(BOARD_SELECT);
         }
         // 페이지네이션 : 전체 페이지 수 반환
         else if(boardDTO.getBoard_condition().equals("BOARD_PAGE_COUNT")) {
            System.out.println("model.BoardDAO.selectOne 게시판 전체 데이터 수 반환하기 쿼리 읽어오기 시작");
            
            if(boardDTO.getBoard_searchKeyword() != null && !boardDTO.getBoard_searchKeyword().isEmpty()) {
               System.out.println("model.BoardDAO.selectOne 페이지 수 : 키워드 있을 때 쿼리");
               // 만약 검색어가 null이 아니고, 빈 문자열도 아닐 때 (검색어가 있을 때)
               // 동적 쿼리를 사용하여 기본 + AND 쿼리 
               pstmt=conn.prepareStatement(BOARD_TOTAL_PAGE +" "+ BOARD_SEARCH_PAGE);
               // 검색어를 찾기 위해 파라미터에 값을 넣어준다.
               pstmt.setString(1, boardDTO.getBoard_searchKeyword()); // 검색어
               pstmt.setString(2, boardDTO.getBoard_searchKeyword()); // 검색어
            }
            else if(boardDTO.getBoard_writer_id() != null && !boardDTO.getBoard_writer_id().isEmpty()) {
               // 내가 쓴 글 전체보기일 때
               System.out.println("model.BoardDAO.selectOne 페이지 수 : 내가 쓴 글 전체보기 쿼리");
               // 동적 쿼리를 사용하여 기본 + AND 쿼리 
               pstmt=conn.prepareStatement(BOARD_TOTAL_PAGE +" "+ BOARD_MYBOARD_PAGE);
               // 파라미터에 값을 넣어준다
               pstmt.setString(1, boardDTO.getBoard_writer_id()); // 회원 ID
            }
            else {
               System.out.println("model.BoardDAO.selectOne 페이지 수 : 전체보기 쿼리");
               // 검색 조건 없이 전체 출력일 때
               pstmt=conn.prepareStatement(BOARD_TOTAL_PAGE);
            }
         }
         else {
            System.out.println("model.BoardDAO.selectOne SQL문 읽어오기 Condition 실패");
         }

         // R이기 때문에 ResultSet 객체가 반환되고, executeQuery 사용한다.
         ResultSet rs=pstmt.executeQuery();

         // next() 객체를 사용해서 한 줄씩 읽어오며 data에 값을 넣어준다.
         // selectOne이기 때문에 그냥 if문 사용
         if(rs.next()) {
            data=new BoardDTO();
            System.out.println("model.BoardDAO.selectOne 쿼리 읽어오기 시작");      

            if(boardDTO.getBoard_condition().equals("BOARD_SELECT_ONE")) {
               // 글 상세보기
               System.out.println("model.BoardDAO.selectOne 글 상세보기 data 추가 시작");
               data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
               data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
               data.setBoard_content(rs.getString("BOARD_CONTENT")); // 글 내용

               // DATE 값을 LocalDate 타입으로 변환 - 24.09.03 LocalDate → Date 타입으로 변경
               data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
               data.setBoard_writer_id(rs.getString("MEMBER_ID")); // 작성자 ID
               data.setBoard_writer_nickname(rs.getString("MEMBER_NICKNAME"));// 작성자 (닉네임)
               data.setBoard_writer_level(rs.getInt("MEMBER_LEVEL")); // 작성자 레벨
               data.setBoard_like_cnt(rs.getInt("LIKE_COUNT")); // 글 좋아요 수
            }
            else if(boardDTO.getBoard_condition().equals("LAST_BOARD_NUM")) {
               // 가장 마지막 게시글 번호 받아오기
               System.out.println("model.BoardDAO.selectOne 가장 마지막 업로드된 글 번호 data 추가 시작");
               data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
            }
            else if(boardDTO.getBoard_condition().equals("BOARD_NUM_SELECTONE")) {
               // 크롤링 select 위해 게시글 번호
               System.out.println("model.BoardDAO.selectOne 크롤링위해 글 번호 받아오기 data 추가 시작");
               data.setBoard_num(rs.getInt("MAX_NUM"));
            }
            else if(boardDTO.getBoard_condition().equals("BOARD_SELECT")) {
               // 크롤링 확인 전체 출력
               System.out.println("model.BoardDAO.selectOne 게시판 데이터 확인을 위한 추가 시작");
               data.setBoard_num(rs.getInt("BOARD_NUM")); // 글 번호
               data.setBoard_title(rs.getString("BOARD_TITLE")); // 글 제목
               data.setBoard_writer_id(rs.getString("MEMBER_ID")); // 작성자 ID
               data.setBoard_content(rs.getString("BOARD_CONTENT")); // 글 내용
               data.setBoard_registration_date(rs.getDate("BOARD_REGISTRATION_DATE")); // 글 작성일
            }
            else if(boardDTO.getBoard_condition().equals("BOARD_PAGE_COUNT")) {
               // 페이지네이션 기능으로 인하여 전체 페이지 수 반환
               System.out.println("model.BoardDAO.selectOne 전체 페이지 수 data 추가 시작");
               data.setBoard_total_page(rs.getInt("BOARD_TOTAL_PAGE")); // 전체 페이지 수
            }
            else {
               System.out.println("model.BoardDAO.selectOne DTO 객체에 담기 Condition 실패");
            }
            System.out.println("model.BoardDAO.selectOne data : " + data);
         }
      } catch (SQLException e) {
         System.err.println("SQL문 실패");
      }

      // [4] 단계 연결해제
      JDBCUtil.disconnect(conn, pstmt);
      System.out.println("model.BoardDAO.selectOne 종료");

      return data;
   }
}