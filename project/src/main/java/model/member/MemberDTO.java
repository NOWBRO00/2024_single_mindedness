package model.member;

import java.sql.Date;

public class MemberDTO { // 회원
	private String member_id; // 아이디 pk
	private String member_password; // 비밀번호
	private String member_nickname; // 닉네임
	private String member_name; // 이름
	private String member_profile; // 프로필 사진
	private Date member_registration_date; // 가입 날짜
	private String member_phone; // 전화 번호
	private String member_condition; // 컨디션
	private String member_address; // 주소
	private String member_role; // 유저, 사장님, 관리자 구분
	
	// 주소api로 주소를 받으면 항목별로 구분해서 들어옴. 이로 인한 멤버변수 생성
	private String member_postcode; // 우편번호
	private String member_extraAddress; // 주소 참고항목
	private String member_detailAddress; // 상세주소
	private int member_level; // 회원의 레벨
	
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_password() {
		return member_password;
	}
	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}
	public String getMember_nickname() {
		return member_nickname;
	}
	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_profile() {
		return member_profile;
	}
	public void setMember_profile(String member_profile) {
		this.member_profile = member_profile;
	}
	public Date getMember_registration_date() {
		return member_registration_date;
	}
	public void setMember_registration_date(Date member_registration_date) {
		this.member_registration_date = member_registration_date;
	}
	public String getMember_phone() {
		return member_phone;
	}
	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}
	public String getMember_condition() {
		return member_condition;
	}
	public void setMember_condition(String member_condition) {
		this.member_condition = member_condition;
	}
	public String getMember_address() {
		return member_address;
	}
	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}
	public String getMember_role() {
		return member_role;
	}
	public void setMember_role(String member_role) {
		this.member_role = member_role;
	}
	public String getMember_postcode() {
		return member_postcode;
	}
	public void setMember_postcode(String member_postcode) {
		this.member_postcode = member_postcode;
	}
	public String getMember_extraAddress() {
		return member_extraAddress;
	}
	public void setMember_extraAddress(String member_extraAddress) {
		this.member_extraAddress = member_extraAddress;
	}
	public String getMember_detailAddress() {
		return member_detailAddress;
	}
	public void setMember_detailAddress(String member_detailAddress) {
		this.member_detailAddress = member_detailAddress;
	}
	public int getMember_level() {
		return member_level;
	}
	public void setMember_level(int member_level) {
		this.member_level = member_level;
	}
	
	
	@Override
	public String toString() {
		return "MemberDTO [member_id=" + member_id + ", member_password=" + member_password + ", member_nickname="
				+ member_nickname + ", member_name=" + member_name + ", member_profile=" + member_profile
				+ ", member_registration_date=" + member_registration_date + ", member_phone=" + member_phone
				+ ", member_condition=" + member_condition + ", member_address=" + member_address + ", member_role="
				+ member_role + ", member_postcode=" + member_postcode + ", member_extraAddress=" + member_extraAddress
				+ ", member_detailAddress=" + member_detailAddress + ", member_level=" + member_level + "]";
	}

	
	
	
}
