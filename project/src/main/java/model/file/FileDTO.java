package model.file;

public class FileDTO { //상품 파일
	private int file_num; // 파일 번호(PK)
	private String file_name; // 파일 이름 (저장되는 이름)
	private String file_original_name; // 파일 원본 이름
	private String file_extension; // 파일 확장자
	private String file_dir; // 파일 경로(URL)
	private int file_board_num; // 글 번호(FK)
	private int file_product_num; // 글 번호(FK)
	
	// DTO에만 있는 멤버변수
	private String file_condition; // 크롤링 인설트 컨디션

	
	
	
	public int getFile_num() {
		return file_num;
	}

	public void setFile_num(int file_num) {
		this.file_num = file_num;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_original_name() {
		return file_original_name;
	}

	public void setFile_original_name(String file_original_name) {
		this.file_original_name = file_original_name;
	}

	public String getFile_extension() {
		return file_extension;
	}

	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}

	public String getFile_dir() {
		return file_dir;
	}

	public void setFile_dir(String file_dir) {
		this.file_dir = file_dir;
	}

	public int getFile_board_num() {
		return file_board_num;
	}

	public void setFile_board_num(int file_board_num) {
		this.file_board_num = file_board_num;
	}

	public int getFile_product_num() {
		return file_product_num;
	}

	public void setFile_product_num(int file_product_num) {
		this.file_product_num = file_product_num;
	}

	public String getFile_condition() {
		return file_condition;
	}

	public void setFile_condition(String file_condition) {
		this.file_condition = file_condition;
	}

	
	
	
	
	
}