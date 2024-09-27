package model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;



public class MemberDAO {
	// SQL 삽입 명령어 
	private final String MEMBER_INSERT="INSERT INTO MEMBER(MEMBER_ID, MEMBER_PASSWORD, MEMBER_NICKNAME, MEMBER_NAME, MEMBER_PHONE, MEMBER_ADDRESS, MEMBER_ROLE) VALUES (?, ?, ?, ?, ?, ?, ?)";

	// SQL 삭제 명령어   
	private final String MEMBER_DELETE="DELETE FROM MEMBER WHERE MEMBER_ID=?";

	// SQL 업데이트 명령어
	private final String UPDATE_MEMBER_PROFILE = "UPDATE MEMBER SET MEMBER_PROFILE = ? WHERE MEMBER_ID = ?"; // 프로필 사진 변경 쿼리
	private final String UPDATE_MEMBER_PASSWORD = "UPDATE MEMBER SET MEMBER_PASSWORD = ? WHERE MEMBER_ID = ?"; // 비밀번호 변경 쿼리
	private final String UPDATE_NAME_NICKNAME = "UPDATE MEMBER SET MEMBER_NAME = COALESCE(?,MEMBER_NAME), MEMBER_NICKNAME = COALESCE(?,MEMBER_NICKNAME) WHERE MEMBER_ID = ?"; 	// 이름 + 닉네임 변경
	private final String UPDATE_MEMBER_ROLE ="UPDATE MEMBER SET MEMBER_ROLE='BANNED' WHRE MEMBER_ID = ?"; // 계정 차단

	// SQL 전체 출력 명령어
	private final String MEMBER_SELECTALL = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NICKNAME,MEMBER_NAME,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_PHONE,MEMBER_ADDRESS,MEMBER_ROLE FROM MEMBER WHERE MEMBER_REGISTRATION_DATE >= CURRENT_DATE - INTERVAL '7' DAY";
	// SQL 쿼리: 가입일이 일주일 이내인 회원 선택
	//CURRENT_DATE - INTERVAL '7' DAY는 현재 날짜에서 7일을 뺀 날짜를 기준으로 가입일을 필터링
	

	// SQL 한개 출력 명령어
	private final String SELECTONE_MEMBER_ID = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NICKNAME,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE FROM MEMBER WHERE MEMBER_ID = ?"; // 아이디(이메일) 중복검사
	private final String SELECTONE_MEMBER_NICKNAME = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NICKNAME,MEMBER_NAME,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE FROM MEMBER WHERE MEMBER_NICKNAME = ?"; // 닉네임 중복검사
	private final String SELECTONE_LOGIN = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NICKNAME,MEMBER_NAME,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PASSWORD = ?"; // 로그인 확인
	private final String SELECTONE_MYPAGE = "SELECT MEMBER_ID,MEMBER_NAME,MEMBER_NICKNAME,MEMBER_PHONE,MEMBER_ADDRESS,MEMBER_PROFILE FROM MEMBER WHERE MEMBER_ID = ?"; // 마이페이지
	private final String SELECTONE_MEMBER_LEVEL = "SELECT MEMBER_ID,MEMBER_LEVEL FROM MEMBER M\r\n"
			+ "JOIN (SELECT B.BOARD_WRITER_ID,COUNT(B.BOARD_WRITER_ID) AS MEMBER_LEVEL FROM BOARD B GROUP BY B.BOARD_WRITER_ID)ML ON M.MEMBER_ID=ML.BOARD_WRITER_ID\r\n"
			+ "WHERE MEMBER_ID=?"; // 회원의 레벨
	private final String SELECTONE_MEMBER_PASSWORD = "SELECT MEMBER_ID, MEMBER_PASSWORD FROM MEMBER WHERE MEMBER_ID = ?"; // 회원의 비밀번호
	// 회원 ID 랜덤으로 출력을 위한 member_id 전체 select
	private final String RANDOM_MEMBER_ID="SELECT MEMBER_ID FROM (SELECT MEMBER_ID FROM MEMBER ORDER BY DBMS_RANDOM.VALUE)WHERE ROWNUM = 1";;
	
	public boolean insert(MemberDTO memberDTO){   // 입력
		System.out.println("member.MemberDAO.insert 시작");
		// 회원가입
		Connection conn=JDBCUtil.connect(); // JDBC연결
		PreparedStatement pstmt=null;   // SQL 실행
		//LocalDate localDate = memberDTO.getMember_registration_date();
		//memberDTO 객체에서 getmember_registration_date() 메서드를 호출하여 
		//반환된 날짜 값을 LocalDate 타입의 변수 localDate에 저장
		try {
			pstmt=conn.prepareStatement(MEMBER_INSERT);//INSERT 실행

			pstmt.setString(1, memberDTO.getMember_id()); // 아이디
			pstmt.setString(2, memberDTO.getMember_password()); // 비밀번호
			pstmt.setString(3, memberDTO.getMember_nickname()); // 닉네임
			pstmt.setString(4, memberDTO.getMember_name()); // 이름
			pstmt.setString(5, memberDTO.getMember_phone()); // 전화번호
			pstmt.setString(6, memberDTO.getMember_address()); // 주소
			pstmt.setString(7, memberDTO.getMember_role()); // 유저 구분

			int result=pstmt.executeUpdate();
			if(result<=0) {
				System.err.println("member.MemberDAO.insert 실패");
				return false;
			}
		}
		catch (SQLException e) {
			System.err.println("member.MemberDAO.insert SQL문 실패"+e.getMessage());
			return false;
		} finally {
			JDBCUtil.disconnect(conn,pstmt); // 종료
		}
		System.out.println("member.MemberDAO.insert 성공");
		return true;
	}

	public boolean update(MemberDTO memberDTO){   // 업데이트
		System.out.println("member.MemberDAO.update 시작");
		Connection conn=JDBCUtil.connect();   //sql DB연결
		PreparedStatement pstmt=null; //sql 실행

		try {
			// 프로필(사진) 변경
			if(memberDTO.getMember_condition().equals("MEMBER_PROFILE")) {
				System.out.println("로그 : MEMBER_PROFILE 시작");
				pstmt = conn.prepareStatement(UPDATE_MEMBER_PROFILE);

				pstmt.setString(1, memberDTO.getMember_profile()); // 새 프로필 사진
				pstmt.setString(2, memberDTO.getMember_id()); // 회원 아이디
			}
			// 비밀번호 변경
			else if(memberDTO.getMember_condition().equals("PASSWORD")) {
				pstmt = conn.prepareStatement(UPDATE_MEMBER_PASSWORD);

				pstmt.setString(1, memberDTO.getMember_password()); // 새 비밀번호
				pstmt.setString(2, memberDTO.getMember_id()); // 회원 아이디
			}
			// 이름+닉네임 변경
			else if(memberDTO.getMember_condition().equals("NAME")) {
				pstmt = conn.prepareStatement(UPDATE_NAME_NICKNAME);

				pstmt.setString(1, memberDTO.getMember_name()); // 회원 이름
				pstmt.setString(2, memberDTO.getMember_nickname()); // 회원 닉네임
				pstmt.setString(3, memberDTO.getMember_id()); // 회원 ID
			}
			// 계정 차단
			else if(memberDTO.getMember_condition().equals("MEMBER_ROLE")) {
				pstmt =conn.prepareStatement(UPDATE_MEMBER_ROLE);
				pstmt.setString(1, memberDTO.getMember_role());
			}
			else {
				System.err.println("컨디션 에러");
				return false;
			}
			int result=pstmt.executeUpdate();
			if(result<=0) {
				System.err.println("member.MemberDAO.update 실패");
				return false;
			}
		}
		catch (SQLException e) {
			System.err.println("member.MemberDAO.update SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(conn,pstmt); // 종료
		}
		System.out.println("member.MemberDAO.update 성공");
		return true;
	}


	public boolean delete(MemberDTO memberDTO){ // 지우기
		//회원 탈퇴
		System.out.println("member.MemberDAO.delete 시작");
		Connection conn=JDBCUtil.connect();//연결
		PreparedStatement pstmt=null; // 실행
		try {
			pstmt=conn.prepareStatement(MEMBER_DELETE);//SQL에서 DELETE실행
			pstmt.setString(1, memberDTO.getMember_id());
			int result=pstmt.executeUpdate();
			if(result<=0) {
				System.err.println("member.MemberDAO.delete 실패");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("member.MemberDAO.delete SQL문 실패");
			return false;
		} finally {
			JDBCUtil.disconnect(conn,pstmt); // 종료
		}
		System.out.println("member.MemberDAO.delete 성공");
		return true;
	}


	public ArrayList<MemberDTO> selectAll(MemberDTO memberDTO){ // 전체 출력
		System.out.println("member.MemberDAO.selectAll 시작");
		ArrayList<MemberDTO> datas=new ArrayList<MemberDTO>();
		// 신규 회원 목록 출력(일주일)
		Connection conn= null;   //sql DB연결
		PreparedStatement pstmt= null; //sql 실행
		ResultSet rs = null;
		conn = JDBCUtil.connect(); // SQL DB 연결
		MemberDTO data = new MemberDTO();
		try {
			if(memberDTO.getMember_condition().equals("MEMBER_ALL")) {
				pstmt = conn.prepareStatement(MEMBER_SELECTALL);
				
				rs = pstmt.executeQuery();// 쿼리 실행
				// 결과를 ArrayList<MemberDTO>에 추가
				while (rs.next()) {
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 비밀번호
					data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
					data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
					data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
					//data.setmember_registration_date(rs.getLocalDate("member_registration_date")); // 가입 날짜
					// 가입 날짜를 java.sql.Date로 변환한 후 LocalDate로 변환
					//Date sqlDate = rs.getDate("MEMBER_REGISTRATION_DATE");
					data.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE")); // 가입 날짜
					datas.add(data);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("member.MemberDAO.selectAll SQL문 실패"+e.getMessage());
		}
		JDBCUtil.disconnect(conn,pstmt); // 연결 종료
		System.out.println("member.MemberDAO.selectAll 성공");
		return datas;
	}

	public MemberDTO selectOne(MemberDTO memberDTO){// 한명 출력
		System.out.println("member.MemberDAO.selectOne 시작");
		MemberDTO data=null;
		Connection conn= null;   //sql DB연결
		PreparedStatement pstmt= null; //sql 실행
		//ResultSet rs = null; 
		try {
			conn = JDBCUtil.connect(); // SQL DB 연결
			if (memberDTO.getMember_condition().equals("CHECK_MEMBER_ID")) {
				// 아이디(이메일) 중복검사    
				System.out.println("model.MemberDAO.selectOne 이메일 중복검사 컨디션 성공");
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_ID);   
				pstmt.setString(1, memberDTO.getMember_id());

			} 
			else if (memberDTO.getMember_condition().equals("CHECK_MEMBER_NICKNAME")) {
				// 닉네임 중복검사
				System.out.println("model.MemberDAO.selectOne 닉네임 중복검사 컨디션 성공");
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_NICKNAME);
				pstmt.setString(1, memberDTO.getMember_nickname());

			} 
			else if (memberDTO.getMember_condition().equals("LOGIN")) {
				System.out.println("model.MemberDAO.selectOne 로그인 컨디션 성공");
				// 로그인
				pstmt = conn.prepareStatement(SELECTONE_LOGIN);
				pstmt.setString(1, memberDTO.getMember_id());
				pstmt.setString(2, memberDTO.getMember_password());

			} 
			else if(memberDTO.getMember_condition().equals("MYPAGE")) {
				// 마이페이지
				System.out.println("model.MemberDAO.selectOne 마이페이지 컨디션 성공");
				pstmt = conn.prepareStatement(SELECTONE_MYPAGE);
				pstmt.setString(1, memberDTO.getMember_id());
			} 
			else if(memberDTO.getMember_condition().equals("MEMBER_LEVEL")) {
				// 회원의 레벨 반환
				System.out.println("model.MemberDAO.selectOne 회원 레벨 반환 컨디션 성공");
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_LEVEL);
				pstmt.setString(1, memberDTO.getMember_id());
			}
			else if(memberDTO.getMember_condition().equals("MEMBER_PASSWORD_GET")) {
				// 회원의 비밀번호 반환
				System.out.println("model.MemberDAO.selectOne 회원 비밀번호 반환 컨디션 성공");
				pstmt = conn.prepareStatement(SELECTONE_MEMBER_PASSWORD);
				pstmt.setString(1, memberDTO.getMember_id());
			}
			else if(memberDTO.getMember_condition().equals("RANDOM_MEMBER_ID")) {
				System.out.println("model.MemberDAO.selectOne 회원 아이디 반환 컨디션 성공");
				pstmt = conn.prepareStatement(RANDOM_MEMBER_ID);
			}
			else {
				System.err.println("컨디션 에러");
				return null;
			}

			System.out.println("246 로그 : id " + memberDTO.getMember_id());
			System.out.println("247 로그 : pw " + memberDTO.getMember_password());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("model.MemberDAO.selectOne rs.next()실행");
				data = new MemberDTO();

				if (memberDTO.getMember_condition().equals("CHECK_MEMBER_ID")) {
					// 아이디(이메일) 중복검사   
					System.out.println("model.MemberDAO.selectOne 이메일 중복검사 객체에 담기");
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 비밀번호
					data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
					data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
					data.setMember_phone(rs.getString("MEMBER_PHONE"));
					data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
					data.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE")); // 가입날짜
				} 
				else if (memberDTO.getMember_condition().equals("CHECK_MEMBER_NICKNAME")) {
					// 닉네임 중복검사
					System.out.println("model.MemberDAO.selectOne 닉네임 중복검사 객체에 담기");
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 비밀번호
					data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
					data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
					data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
					data.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE")); // 가입날짜		
				}
				else if (memberDTO.getMember_condition().equals("LOGIN")) {
					// 로그인
					System.out.println("model.MemberDAO.selectOne 로그인 객체에 담기");
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 비밀번호
					data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
					data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
					data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
					data.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE")); // 가입날짜
				}
				else if(memberDTO.getMember_condition().equals("MYPAGE")) {
					// 마이페이지
					System.out.println("model.MemberDAO.selectOne 마이페이지 객체에 담기");
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_name(rs.getString("MEMBER_NAME")); // 이름
					data.setMember_nickname(rs.getString("MEMBER_NICKNAME")); // 닉네임
					data.setMember_phone(rs.getString("MEMBER_PHONE")); // 핸드폰 번호
					data.setMember_address(rs.getString("MEMBER_ADDRESS")); // 주소
					data.setMember_profile(rs.getString("MEMBER_PROFILE")); // 프로필 사진
				}
				else if(memberDTO.getMember_condition().equals("MEMBER_LEVEL")) {
					// 회원의 레벨 반환
					System.out.println("model.MemberDAO.selectOne 회원 레벨 반환 객체에 담기");
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_level(rs.getInt("MEMBER_LEVEL")); // 회원의 레벨
				}
				else if(memberDTO.getMember_condition().equals("MEMBER_PASSWORD_GET")) {
					// 회원의 비밀번호 반환
					System.out.println("model.MemberDAO.selectOne 회원 비밀번호 반환 객체에 담기");
					data.setMember_id(rs.getString("MEMBER_ID")); // 아이디
					data.setMember_password(rs.getString("MEMBER_PASSWORD")); // 회원의 비밀번호
				}
				else if(memberDTO.getMember_condition().equals("RANDOM_MEMBER_ID")) {
					System.out.println("model.MemberDAO.selectOne 회원 아이디 반환 객체에 담기");
					data.setMember_id(rs.getString("MEMBER_ID"));
				}
				System.out.println("member.MemberDAO.selectOne data : "+data);
			}
			else{
				System.out.println("307 로그 : rs.next()가 false를 반환했습니다.");
			}
		}
		catch (SQLException e) {
			System.err.println("SQL문 실패: " + e.getMessage());
			e.printStackTrace();  
			System.err.println("member.MemberDAO.selectOne SQL문 실패");
		}
		JDBCUtil.disconnect(conn,pstmt); // 연결 종료

		System.out.println("member.MemberDAO.selectOne 종료");
		return data;
	}
}
