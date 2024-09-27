package model.reply;

import java.sql.Date;

public class ReplyDTO { // 댓글창
	private int reply_num; // 댓글 번호 - PK
	private String reply_content;	// 댓글 내용
	private String reply_writer_id;	// 댓글 작성자 - FK
	private Date reply_registration_date;	// 댓글 작성 날짜
	private int reply_private; // 비공개, 0이면 공개 1이면 비공개
	private int reply_board_num; // 글 번호- FK
	
	// DTO에만 있는 멤버변수 (테이블에 없음)
	private String reply_writer_nickname;	// 댓글 작성자 닉네임
	private int reply_writer_level; // 댓글 작성자 레벨
	private String reply_member_profile; // 프로필 이미지

	public String getReply_member_profile() {
		return reply_member_profile;
	}
	public void setReply_member_profile(String reply_member_profile) {
		this.reply_member_profile = reply_member_profile;
	}
	public int getReply_num() {
		return reply_num;
	}
	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getReply_writer_id() {
		return reply_writer_id;
	}
	public void setReply_writer_id(String reply_writer_id) {
		this.reply_writer_id = reply_writer_id;
	}
	public String getReply_writer_nickname() {
		return reply_writer_nickname;
	}
	public void setReply_writer_nickname(String reply_writer_nickname) {
		this.reply_writer_nickname = reply_writer_nickname;
	}
	public int getReply_writer_level() {
		return reply_writer_level;
	}
	public void setReply_writer_level(int reply_writer_level) {
		this.reply_writer_level = reply_writer_level;
	}
	public Date getReply_registration_date() {
		return reply_registration_date;
	}
	public void setReply_registration_date(Date reply_registration_date) {
		this.reply_registration_date = reply_registration_date;
	}
	public int getReply_private() {
		return reply_private;
	}
	public void setReply_private(int reply_private) {
		this.reply_private = reply_private;
	}
	public int getReply_board_num() {
		return reply_board_num;
	}
	public void setReply_board_num(int reply_board_num) {
		this.reply_board_num = reply_board_num;
	}
	
	

}
