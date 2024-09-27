package model.product;

import java.sql.Date;
import java.util.List;

public class ProductDTO { // 상품
	private int product_num; // 상품 번호(PK)
	private String product_name; // 상품 명
	private int product_price; // 상품 가격
	private String product_details; // 상품 설명
	private int product_cnt; // 상품 재고(자리)-ex.배에 탈 수 있는 정원
	private String product_location; // 상품 장소 (바다, 민물)
	private String product_category; // 상품 유형(낚시배, 낚시터,낚시카페,수상)
	private String product_address; // 상품의 주소
	//private String product_seller_id; // 상품 판매자 정보(FK) - 최프 사용 예정
	
	// DTO에만 있는 멤버변수 (테이블에 없음)
	private String product_condition; //상품 컨디션
	private String product_searchKeyword;// 상품 검색어
	private double product_avg_rating; // 별점 평균
	private int product_payment_cnt; // 상품 결제 수
	private Date product_reservation_date; // 예약일 (예약 선택일)
	private int product_wishlist_cnt; // 찜 수
	private String product_file_name; // 파일 이름
	private String product_file_extension; // 파일 확장자
	private String product_file_dir; // 파일 경로
	private int product_page_num; // 페이지네이션 : 사용자가 선택한 페이지 번호
	private int product_total_page; // 페이지네이션 : 전체 상품 데이터 개수
	private List<String> url; // 크롤링 : 이미지 저장 리스트
	
	
	
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	public String getProduct_details() {
		return product_details;
	}
	public void setProduct_details(String product_details) {
		this.product_details = product_details;
	}
	public int getProduct_cnt() {
		return product_cnt;
	}
	public void setProduct_cnt(int product_cnt) {
		this.product_cnt = product_cnt;
	}
	public String getProduct_location() {
		return product_location;
	}
	public void setProduct_location(String product_location) {
		this.product_location = product_location;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public String getProduct_address() {
		return product_address;
	}
	public void setProduct_address(String product_address) {
		this.product_address = product_address;
	}
	public String getProduct_condition() {
		return product_condition;
	}
	public void setProduct_condition(String product_condition) {
		this.product_condition = product_condition;
	}
	public String getProduct_searchKeyword() {
		return product_searchKeyword;
	}
	public void setProduct_searchKeyword(String product_searchKeyword) {
		this.product_searchKeyword = product_searchKeyword;
	}
	public double getProduct_avg_rating() {
		return product_avg_rating;
	}
	public void setProduct_avg_rating(double product_avg_rating) {
		this.product_avg_rating = product_avg_rating;
	}
	public int getProduct_payment_cnt() {
		return product_payment_cnt;
	}
	public void setProduct_payment_cnt(int product_payment_cnt) {
		this.product_payment_cnt = product_payment_cnt;
	}
	public Date getProduct_reservation_date() {
		return product_reservation_date;
	}
	public void setProduct_reservation_date(Date product_reservation_date) {
		this.product_reservation_date = product_reservation_date;
	}
	public int getProduct_wishlist_cnt() {
		return product_wishlist_cnt;
	}
	public void setProduct_wishlist_cnt(int product_wishlist_cnt) {
		this.product_wishlist_cnt = product_wishlist_cnt;
	}
	public String getProduct_file_name() {
		return product_file_name;
	}
	public void setProduct_file_name(String product_file_name) {
		this.product_file_name = product_file_name;
	}
	public String getProduct_file_extension() {
		return product_file_extension;
	}
	public void setProduct_file_extension(String product_file_extension) {
		this.product_file_extension = product_file_extension;
	}
	public String getProduct_file_dir() {
		return product_file_dir;
	}
	public void setProduct_file_dir(String product_file_dir) {
		this.product_file_dir = product_file_dir;
	}
	public int getProduct_page_num() {
		return product_page_num;
	}
	public void setProduct_page_num(int product_page_num) {
		this.product_page_num = product_page_num;
	}
	public int getProduct_total_page() {
		return product_total_page;
	}
	public void setProduct_total_page(int product_total_page) {
		this.product_total_page = product_total_page;
	}
	public List<String> getUrl() {
		return url;
	}
	public void setUrl(List<String> url) {
		this.url = url;
	}
	
	
	@Override
	public String toString() {
		return "ProductDTO [product_num=" + product_num + ", product_name=" + product_name + ", product_price="
				+ product_price + ", product_details=" + product_details + ", product_cnt=" + product_cnt
				+ ", product_location=" + product_location + ", product_category=" + product_category
				+ ", product_address=" + product_address + ", product_condition=" + product_condition
				+ ", product_searchKeyword=" + product_searchKeyword + ", product_avg_rating=" + product_avg_rating
				+ ", product_payment_cnt=" + product_payment_cnt + ", product_reservation_date="
				+ product_reservation_date + ", product_wishlist_cnt=" + product_wishlist_cnt + ", product_file_name="
				+ product_file_name + ", product_file_extension=" + product_file_extension + ", product_file_dir="
				+ product_file_dir + ", product_page_num=" + product_page_num + ", product_total_page="
				+ product_total_page + ", url=" + url + "]";
	}
	
	
	
	
}
