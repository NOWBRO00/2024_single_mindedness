package controller.common;

import java.util.ArrayList;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.board.BoardDAO;
import model.board.BoardDTO;
import model.common.CrawlingSelenium;
import model.file.FileDAO;
import model.file.FileDTO;
import model.product.ProductDAO;
import model.product.ProductDTO;

@WebListener
public class SampleListener implements ServletContextListener {
    // DAO 및 크롤링 객체를 멤버변수 로 선언
   private ProductDAO productDAO; // 상품
    private BoardDAO boardDAO; // 게시판
    private FileDAO fileDAO; // 이미지
    private CrawlingSelenium crawling; // 크롤링
    
   public SampleListener() {
   }

   // 웹 애플리케이션이 초기화될 때 호출되는 메서드
   public void contextInitialized(ServletContextEvent sce) {
      // 서버 구동 시 로그 출력
      System.out.println("웹 서버 구동(실행)을 감지함");

        // 객체 생성
        crawling = new CrawlingSelenium(); // 크롤링 객체
        productDAO = new ProductDAO(); // 상품 DAO 객체
        boardDAO = new BoardDAO(); // 게시판 DAO 객체
        fileDAO = new FileDAO(); // 파일 DAO 객체

      // 상품 데이터 처리
      processProducts(productDAO, fileDAO);

      // 게시판 데이터 처리
      processBoards(boardDAO, fileDAO);

      // 크롤링 종료
      crawling.CrawlingSeleniumDown();
      System.out.println("웹 서버 구동(실행)을 종료");
   }

   // 상품 처리 메서드
   private void processProducts(ProductDAO productDAO, FileDAO fileDAO) {
      // 상품 크롤링
      // 객체 생성
      ProductDTO productDTO = new ProductDTO();
      // 컨디션
      productDTO.setProduct_condition("PRODUCT_SELECT");   
      // 제품 데이터가 없을 경우 크롤링 수행
      if (productDAO.selectOne(productDTO) == null) {
         System.out.println("56 로그: 상품 데이터가 없음. 크롤링 시작");
         // 각 상품 종류별 데이터 삽입 시작
         // 상품, 이미지 , 크롤링, 컨디션
         System.out.println("56 로그: 바다 낚시배 데이터 삽입 시작");
         insertProducts(productDAO, fileDAO, CrawlingSelenium.makeSampleProductSeaBoat(), "PRODUCT_CRAWLING_SEA_BOAR_INSERT");
         System.out.println("58 로그: 바다 낚시배 데이터 삽입 완료");

         System.out.println("59 로그: 바다 낚시터 데이터 삽입 시작");
         insertProducts(productDAO, fileDAO, CrawlingSelenium.makeSampleProductSeaFishing(), "PRODUCT_CRAWLING_SEA_FISHING_INSERT");
         System.out.println("61 로그: 바다 낚시터 데이터 삽입 완료");

         System.out.println("62 로그: 민물 낚시터 데이터 삽입 시작");
         insertProducts(productDAO, fileDAO, CrawlingSelenium.makeSampleProductFreshWaterFishing(), "PRODUCT_CRAWLING_FRESH_WATER_FISHING_INSERT");
         System.out.println("64 로그: 민물 낚시터 데이터 삽입 완료");

         System.out.println("65 로그: 민물 낚시터 카페 데이터 삽입 시작");
         insertProducts(productDAO, fileDAO, CrawlingSelenium.makeSampleProductFreshWaterFishingFishingCafe(), "PRODUCT_CRAWLING_FRESH_WATER_FISHING_CAFE_INSERT");
         System.out.println("67 로그: 민물 낚시터 카페 데이터 삽입 완료");
      }
      else {
         System.out.println("product 데이터 있음");
      }
   }

   // 상품 삽입 메서드
   private void insertProducts(ProductDAO productDAO, FileDAO fileDAO, ArrayList<ProductDTO> products, String condition) {
      System.out.println("84 로그: 상품 삽입 시작");
      for (ProductDTO data : products) {// 상품 리스트 만큼 반복
         System.out.println("77 로그: 제품 삽입 시작 - 상품명: [" + data.getProduct_name()+"]");
         data.setProduct_condition(condition); // 제품 상태 설정
         //System.out.println("url"+data.getProduct_file_dir());
         System.out.println("89 로그 컨디션: ["+condition+"]");
         
         // 제품 삽입
         System.out.println("92 로그 data: ["+data+"]");
         boolean isInserted = productDAO.insert(data);
         if (isInserted) { // insert가 true가 되면
            //System.out.println("80 로그: 제품 삽입 완료 - 제품 번호: " + data.getProduct_num());
            data.setProduct_condition("PRODUCT_NUM_SELECT");
            ProductDTO product = productDAO.selectOne(data); // 제품 정보 조회
            System.out.println("98 로그 product: ["+product+"]");
            System.out.println("99 로그 data: ["+data+"]");

            // 이미지 URL을 List로 변환 (.jpg로 구분된 경우)
            //List<String> imageUrls = Arrays.asList(data.getProduct_file_dir());
            //System.out.println("이미지 배열"+imageUrls);
            // 데이터에 들어있는 url 만큼 배열로 만들어서
            
            // 이미지 URL 처리
            if(data.getUrl() != null && !data.getUrl().isEmpty()) {
               for (String imageUrl : data.getUrl()) { 
                  // 배열만큼 반복시킨다. 
                  System.out.println("110 로그: 이미지 삽입 시작");
                  FileDTO imageFileDTO = new FileDTO();
                  imageFileDTO.setFile_condition("FILE_PRODUCT_CRAWLING_INSERT"); // 파일 컨디션 설정
                  imageFileDTO.setFile_original_name(data.getProduct_name()); // 원래 파일명
                  System.out.println("114 로그 producName: ["+data.getProduct_name()+"]");
                  imageFileDTO.setFile_product_num(product.getProduct_num()); // 해당 상품의 번호
                  System.out.println("116 로그 productNum: ["+product.getProduct_num()+"]");
                  imageFileDTO.setFile_dir(imageUrl); // 이미지 URL 설정

                  // 이미지 DB에 삽입
                  fileDAO.insert(imageFileDTO);
                  System.out.println("121 로그: 이미지 삽입 완료");
               }
            } 
         }
         else {
            System.out.println("126 로그 상품 삽입 실패 - 제품명: [" + data.getProduct_name()+"]");
         }
      }
   }

   // 게시판 처리 메서드
   private void processBoards(BoardDAO boardDAO, FileDAO fileDAO) {
      System.out.println("133 로그: 게시판 데이터 처리 시작");
      BoardDTO boardDTO=new BoardDTO();
      // 컨디션
      boardDTO.setBoard_condition("BOARD_SELECT");
      // 게시글 데이터가 없을 경우 크롤링 수행
      if (boardDAO.selectOne(boardDTO) == null) {
         ArrayList<BoardDTO> boards = CrawlingSelenium.makeSampleBoard(); // 게시판 샘플 데이터 생성
         insertBoards(boardDAO, fileDAO, boards); // 게시판 데이터 삽입
         System.out.println("141 로그: 게시판 데이터 처리 완료");
      }
      else {
         System.out.println("board 데이터 있음");
      }
   }

   // 게시판 삽입 메서드
   private void insertBoards(BoardDAO boardDAO, FileDAO fileDAO, ArrayList<BoardDTO> boards) {
      for (BoardDTO data : boards) {

         System.out.println("99 로그: 게시판 삽입 시작 - 게시판 제목: " + data.getBoard_title());
         data.setBoard_condition("BOARD_CRAWLING_INSERT"); // 게시판 컨디션 설정

         // 게시판 삽입
         boolean isInserted = boardDAO.insert(data);
         if (isInserted) { // true 라면 
            System.out.println("102 로그: 게시판 삽입 완료 - 게시판 번호: " + data.getBoard_num());
            data.setBoard_condition("BOARD_NUM_SELECTONE");
            BoardDTO board = boardDAO.selectOne(data);

            if(data.getUrl()!=null&&!data.getUrl().isEmpty()) {
               System.out.println("163 로그: 게시판 이미지 삽입 시작");
               for (String imageUrl : data.getUrl()) {
                  FileDTO imageFileDTO = new FileDTO();
                  imageFileDTO.setFile_condition("FILE_BOARD_CRAWLING_INSERT"); // 파일 상태 설정
                  imageFileDTO.setFile_original_name(data.getBoard_writer_id()); // 원래 파일명
                  imageFileDTO.setFile_board_num(board.getBoard_num()); // 해당 게시판의 번호
                  imageFileDTO.setFile_dir(imageUrl); // 이미지 URL 설정

                  // 이미지 DB에 삽입
                  fileDAO.insert(imageFileDTO);
                  System.out.println("173 로그: 이미지 삽입 완료");

               }
            }
         }else {
            System.out.println("178 로그 게시판 삽입 실패 - 제품명: [" + data.getBoard_title()+"]");
         }
      } 
   }

   /*   
   // 게시판 이미지 삽입 메서드
   private void insertBoardImages(FileDAO fileDAO, BoardDTO board) {
       // 게시판 이미지 URL이 null이 아니고 비어 있지 않은 경우
       if (board.getBoard_file_dir() != null && !board.getBoard_file_dir().isEmpty()) {
           System.out.println("110 로그: 게시판 이미지 삽입 시작 - 게시판 번호: " + board.getBoard_num());

           // 이미지 URL을 쉼표로 구분하여 List로 변환 (필요한 경우)
           List<String> imageUrls = Arrays.asList(board.getBoard_file_dir().split(","));

           for (String imageUrl : imageUrls) {
               // 이미지 URL 공백 제거
               imageUrl = imageUrl.trim();

               // 공백이 제거된 이미지 URL이 비어 있지 않은지 확인
               if (!imageUrl.isEmpty()) {
                   FileDTO imageFileDTO = new FileDTO();
                   imageFileDTO.setFile_condition("FILE_BOARD_CRAWLING_INSERT"); // 파일 상태 설정
                   imageFileDTO.setFile_original_name(board.getBoard_writer_id()); // 원래 파일명
                   imageFileDTO.setFile_item_num(board.getBoard_num()); // 해당 게시판의 번호
                   imageFileDTO.setFile_dir(imageUrl); // 이미지 URL 설정

                   try {
                       // 이미지 DB에 삽입
                       fileDAO.insert(imageFileDTO);
                       System.out.println("114 로그: 게시판 이미지 삽입 완료 - 이미지 URL: " + imageUrl);
                   } catch (Exception e) {
                       System.err.println("115 로그: 게시판 이미지 삽입 실패 - 이미지 URL: " + imageUrl);
                       e.printStackTrace();
                   }
               } else {
                   System.out.println("116 로그: 비어 있는 이미지 URL - 게시판 번호: " + board.getBoard_num());
               }
           }
           System.out.println("118 로그: 게시판 이미지 삽입 완료 - 게시판 번호: " + board.getBoard_num());
       } else {
           System.out.println("119 로그: 이미지가 없음 - 게시판 번호: " + board.getBoard_num());
       }
   }
    */
   // 서버가 종료될 때 호출되는 메서드
   public void contextDestroyed(ServletContextEvent sce) {
      System.out.println("서버 종료 감지, 자원 해제 중...");
        if (crawling != null) {
            crawling.CrawlingSeleniumDown(); // 크롤링 종료
            //CrawlingSeleniumDown(): 크롤링 프로세스를 종료하거나 자원을 해제하는 역할
            System.out.println("229 로그: 크롤링 종료");
        }
        // 각 DAO에서 연결 종료 및 자원 해제 로직 추가
        System.out.println("자원 해제 완료, 서버 종료");
    }
}
