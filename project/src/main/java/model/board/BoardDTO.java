package model.board;

import java.sql.Date;
import java.util.List;

public class BoardDTO { //게시판
   private int board_num; //게시판 번호 : PK
   private String board_title; // 게시판 제목
   private String board_content; // 게시판 내용
   private String board_writer_id; // 게시판 작성자 : FK
   private Date board_registration_date; // 게시판 작성일

   // DTO에만 있는 멤버변수 (테이블에 없음)
   private String board_writer_nickname; // 게시판 작성자 닉네임
   private int board_writer_level; // 작성자 레벨
   private int board_like_cnt; // 게시판 좋아요 개수
   private int board_claim_cnt; // 게시판 신고 개수
   private String board_searchKeyword; // 게시판 검색어
   private String board_condition; // 게시물 컨디션 (기능 구분 기능)
   private String board_file_name; // 파일 이름
   private String board_file_extension; // 파일 확장자
   private String board_file_dir; // 파일 경로
   private int board_page_num; // 페이지네이션 : 사용자 선택 페이지
   private int board_total_page; // 페이지네이션 : 전체 데이터 개수
   private List<String> url; // 크롤링 : 이미지 저장 리스트


   public List<String> getUrl() {
      return url;
   }
   public void setUrl(List<String> url) {
      this.url = url;
   }

   public int getBoard_total_page() {
      return board_total_page;
   }
   public void setBoard_total_page(int board_total_page) {
      this.board_total_page = board_total_page;
   }
   public int getBoard_page_num() {
      return board_page_num;
   }
   public void setBoard_page_num(int board_page_num) {
      this.board_page_num = board_page_num;
   }
   public String getBoard_file_name() {
      return board_file_name;
   }
   public void setBoard_file_name(String board_file_name) {
      this.board_file_name = board_file_name;
   }
   public String getBoard_file_extension() {
      return board_file_extension;
   }
   public void setBoard_file_extension(String board_file_extension) {
      this.board_file_extension = board_file_extension;
   }
   public String getBoard_file_dir() {
      return board_file_dir;
   }
   public void setBoard_file_dir(String board_file_dir) {
      this.board_file_dir = board_file_dir;
   }
   public int getBoard_num() {
      return board_num;
   }
   public void setBoard_num(int board_num) {
      this.board_num = board_num;
   }
   public String getBoard_title() {
      return board_title;
   }
   public void setBoard_title(String board_title) {
      this.board_title = board_title;
   }
   public String getBoard_content() {
      return board_content;
   }
   public void setBoard_content(String board_content) {
      this.board_content = board_content;
   }
   public String getBoard_writer_id() {
      return board_writer_id;
   }
   public void setBoard_writer_id(String board_writer_id) {
      this.board_writer_id = board_writer_id;
   }
   public String getBoard_writer_nickname() {
      return board_writer_nickname;
   }
   public void setBoard_writer_nickname(String board_writer_nickname) {
      this.board_writer_nickname = board_writer_nickname;
   }
   public int getBoard_writer_level() {
      return board_writer_level;
   }
   public void setBoard_writer_level(int board_writer_level) {
      this.board_writer_level = board_writer_level;
   }
   public Date getBoard_registration_date() {
      return board_registration_date;
   }
   public void setBoard_registration_date(Date board_registration_date) {
      this.board_registration_date = board_registration_date;
   }
   public int getBoard_like_cnt() {
      return board_like_cnt;
   }
   public void setBoard_like_cnt(int board_like_cnt) {
      this.board_like_cnt = board_like_cnt;
   }
   public int getBoard_claim_cnt() {
      return board_claim_cnt;
   }
   public void setBoard_claim_cnt(int board_claim_cnt) {
      this.board_claim_cnt = board_claim_cnt;
   }
   public String getBoard_searchKeyword() {
      return board_searchKeyword;
   }
   public void setBoard_searchKeyword(String board_searchKeyword) {
      this.board_searchKeyword = board_searchKeyword;
   }
   public String getBoard_condition() {
      return board_condition;
   }
   public void setBoard_condition(String board_condition) {
      this.board_condition = board_condition;
   }
   @Override
   public String toString() {
      return "BoardDTO [board_num=" + board_num + ", board_title=" + board_title + ", board_content=" + board_content
            + ", board_writer_id=" + board_writer_id + ", board_registration_date=" + board_registration_date
            + ", board_writer_nickname=" + board_writer_nickname + ", board_writer_level=" + board_writer_level
            + ", board_like_cnt=" + board_like_cnt + ", board_claim_cnt=" + board_claim_cnt
            + ", board_searchKeyword=" + board_searchKeyword + ", board_condition=" + board_condition
            + ", board_file_name=" + board_file_name + ", board_file_extension=" + board_file_extension
            + ", board_file_dir=" + board_file_dir + ", board_page_num=" + board_page_num + ", board_total_page="
            + board_total_page + ", url=" + url + "]";
   }






}
