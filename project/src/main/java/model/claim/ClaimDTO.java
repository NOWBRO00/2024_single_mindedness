package model.claim;

public class ClaimDTO {
	private int claim_num; // 신고 번호 - PK
	private String claim_category; // 신고 카테고리
	//private int claim_type; // 신고 유형 - FK(글번호, 댓글 번호)
	private int claim_board_num; // 게시판 FK
	private int claim_reply_num; // 댓글 FK
	private String claim_status; // 신고 처리여부
	private String claim_condition; // 글/댓글 구분
	private String claim_reporter_id; // 신고자 - FK
	private String claim_target_member_id; // 신고당한 유저 - FK
	
	
	
	public int getClaim_board_num() {
		return claim_board_num;
	}
	public void setClaim_board_num(int claim_board_num) {
		this.claim_board_num = claim_board_num;
	}
	public int getClaim_reply_num() {
		return claim_reply_num;
	}
	public void setClaim_reply_num(int claim_reply_num) {
		this.claim_reply_num = claim_reply_num;
	}
	public int getClaim_num() {
		return claim_num;
	}
	public void setClaim_num(int claim_num) {
		this.claim_num = claim_num;
	}
	public String getClaim_category() {
		return claim_category;
	}
	public void setClaim_category(String claim_category) {
		this.claim_category = claim_category;
	}
//	public int getClaim_type() {
//		return claim_type;
//	}
//	public void setClaim_type(int claim_type) {
//		this.claim_type = claim_type;
//	}
	public String getClaim_status() {
		return claim_status;
	}
	public void setClaim_status(String claim_status) {
		this.claim_status = claim_status;
	}
	public String getClaim_condition() {
		return claim_condition;
	}
	public void setClaim_condition(String claim_condition) {
		this.claim_condition = claim_condition;
	}
	public String getClaim_reporter_id() {
		return claim_reporter_id;
	}
	public void setClaim_reporter_id(String claim_reporter_id) {
		this.claim_reporter_id = claim_reporter_id;
	}
	public String getClaim_target_member_id() {
		return claim_target_member_id;
	}
	public void setClaim_target_member_id(String claim_target_member_id) {
		this.claim_target_member_id = claim_target_member_id;
	}
	
	

	
}
