package model.like;

public class LikeDTO {
	private int like_num; // 좋아요 번호 - PK
	private int like_board_num; // 좋아요 받은 글 - FK
	private String like_member_id; // 좋아요 누른 사람 - FK
	
	
	
	public int getLike_num() {
		return like_num;
	}
	public void setLike_num(int like_num) {
		this.like_num = like_num;
	}
	public int getLike_board_num() {
		return like_board_num;
	}
	public void setLike_board_num(int like_board_num) {
		this.like_board_num = like_board_num;
	}
	public String getLike_member_id() {
		return like_member_id;
	}
	public void setLike_member_id(String like_member_id) {
		this.like_member_id = like_member_id;
	}
	
}
