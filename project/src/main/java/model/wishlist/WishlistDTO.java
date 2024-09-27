package model.wishlist;

public class WishlistDTO {
	private int wishlist_num; // 위시리스트 번호 - PK
	private String wishlist_member_id; // 사용자 ID - FK
	private int wishlist_product_num; // 상품 번호 - FK
	
	// DTO에만 있는 멤버변수 (테이블에 없음)
	private String wishlist_product_name; // 상품명
	private int wishlist_product_price; // 상품 가격
	private String wishlist_product_location; // 상품 장소 (바다,민물)
	private String wishlist_product_category; // 상품 유형 (낚시배, 낚시터, 낚시카페, 수상)
	private String wishlist_product_address; // 상품 주소
	private String wishlist_file_name; // 파일 이름
	private String wishlist_file_extension; // 파일 확장자
	private String wishlist_file_dir; // 파일 경로
	private int wishlist_count; // 위시리스트 개수
	private int wishlist_page_num; // 페이지네이션 : 사용자가 선택한 페이지 번호
	private int wishlist_total_page; // 페이지네이션 : 위시리스트 페이지 수
	private String wishlist_condition; // 컨디션
	
	
	
	public int getWishlist_num() {
		return wishlist_num;
	}
	public void setWishlist_num(int wishlist_num) {
		this.wishlist_num = wishlist_num;
	}
	public String getWishlist_member_id() {
		return wishlist_member_id;
	}
	public void setWishlist_member_id(String wishlist_member_id) {
		this.wishlist_member_id = wishlist_member_id;
	}
	public int getWishlist_product_num() {
		return wishlist_product_num;
	}
	public void setWishlist_product_num(int wishlist_product_num) {
		this.wishlist_product_num = wishlist_product_num;
	}
	public String getWishlist_product_name() {
		return wishlist_product_name;
	}
	public void setWishlist_product_name(String wishlist_product_name) {
		this.wishlist_product_name = wishlist_product_name;
	}
	public int getWishlist_product_price() {
		return wishlist_product_price;
	}
	public void setWishlist_product_price(int wishlist_product_price) {
		this.wishlist_product_price = wishlist_product_price;
	}
	public String getWishlist_product_location() {
		return wishlist_product_location;
	}
	public void setWishlist_product_location(String wishlist_product_location) {
		this.wishlist_product_location = wishlist_product_location;
	}
	public String getWishlist_product_category() {
		return wishlist_product_category;
	}
	public void setWishlist_product_category(String wishlist_product_category) {
		this.wishlist_product_category = wishlist_product_category;
	}
	public String getWishlist_product_address() {
		return wishlist_product_address;
	}
	public void setWishlist_product_address(String wishlist_product_address) {
		this.wishlist_product_address = wishlist_product_address;
	}
	public String getWishlist_file_name() {
		return wishlist_file_name;
	}
	public void setWishlist_file_name(String wishlist_file_name) {
		this.wishlist_file_name = wishlist_file_name;
	}
	public String getWishlist_file_extension() {
		return wishlist_file_extension;
	}
	public void setWishlist_file_extension(String wishlist_file_extension) {
		this.wishlist_file_extension = wishlist_file_extension;
	}
	public String getWishlist_file_dir() {
		return wishlist_file_dir;
	}
	public void setWishlist_file_dir(String wishlist_file_dir) {
		this.wishlist_file_dir = wishlist_file_dir;
	}
	public int getWishlist_count() {
		return wishlist_count;
	}
	public void setWishlist_count(int wishlist_count) {
		this.wishlist_count = wishlist_count;
	}
	public int getWishlist_page_num() {
		return wishlist_page_num;
	}
	public void setWishlist_page_num(int wishlist_page_num) {
		this.wishlist_page_num = wishlist_page_num;
	}
	public int getWishlist_total_page() {
		return wishlist_total_page;
	}
	public void setWishlist_total_page(int wishlist_total_page) {
		this.wishlist_total_page = wishlist_total_page;
	}
	public String getWishlist_condition() {
		return wishlist_condition;
	}
	public void setWishlist_condition(String wishlist_condition) {
		this.wishlist_condition = wishlist_condition;
	}
	
	
	
	@Override
	public String toString() {
		return "WishlistDTO [wishlist_num=" + wishlist_num + ", wishlist_member_id=" + wishlist_member_id
				+ ", wishlist_product_num=" + wishlist_product_num + ", wishlist_product_name=" + wishlist_product_name
				+ ", wishlist_product_price=" + wishlist_product_price + ", wishlist_product_location="
				+ wishlist_product_location + ", wishlist_product_category=" + wishlist_product_category
				+ ", wishlist_product_address=" + wishlist_product_address + ", wishlist_file_name="
				+ wishlist_file_name + ", wishlist_file_extension=" + wishlist_file_extension + ", wishlist_file_dir="
				+ wishlist_file_dir + ", wishlist_count=" + wishlist_count + ", wishlist_page_num=" + wishlist_page_num
				+ ", wishlist_total_page=" + wishlist_total_page + ", wishlist_condition=" + wishlist_condition + "]";
	}
	
	
	
	
	
	
}
