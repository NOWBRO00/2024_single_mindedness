package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.member.MemberDAO;
import model.member.MemberDTO;

public class JoinAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("JoinAction 시작");
//		mid
//		emailStatus
//		password
//		nickName
//		name
//		phoneNumber
//		address
//		사장님인지 아닌지 
		String member_id=request.getParameter("member_id");
		String member_password=request.getParameter("member_password");
		String member_nickname=request.getParameter("member_nickname");
		String member_name=request.getParameter("member_name");
		String member_phone=request.getParameter("member_phone");
		String postcode=request.getParameter("postcode"); // 우편번호
		String address=request.getParameter("address"); // 주소
		String extraAddress=request.getParameter("extraAddress"); // 주가 주소
		String detailAddress=request.getParameter("detailAddress"); // 상세 주소
		String member_role=request.getParameter("member_role");
		System.out.println(member_role);

		//데이터 로그
		System.out.println("member_id : "+member_id);
		System.out.println("member_pw : "+member_password);
		System.out.println("member_nickname : "+member_nickname);
		System.out.println("member_name : "+member_name);
		System.out.println("member_phone : "+member_phone);
		System.out.println("postcode : "+postcode);
		System.out.println("address : "+address);
		System.out.println("extraAddress : "+extraAddress);
		System.out.println("detailAddress : "+detailAddress);
		System.out.println("member_role : "+member_role);
		
		// 전체 주소 
		String totalAddress =postcode+"_"+address+"_"+extraAddress+"_"+detailAddress;
		System.out.println(totalAddress);
		
		
		MemberDTO memberDTO=new MemberDTO();
		memberDTO.setMember_id(member_id); // 아이디
		memberDTO.setMember_password(member_password); // 비밀번호
		memberDTO.setMember_nickname(member_nickname); // 닉네임
		memberDTO.setMember_name(member_name); // 이름
		memberDTO.setMember_phone(member_phone); // 전화번호
		memberDTO.setMember_address(totalAddress); // 주소 세개 받아와서 붙이기
		memberDTO.setMember_role(member_role); // 사장님 / 사용자 
		
		MemberDAO memberDAO=new MemberDAO();
		boolean flag=memberDAO.insert(memberDTO); // insert
		ActionForward forward=new ActionForward();
		if(flag) { // 성공이라면
			System.out.println("JoinAction 로그 : 회원가입 성공");
			forward.setRedirect(false); 
			forward.setPath("loginPage.do"); // 로그인 페이지로 리다이렉트
		}
		else { // 실패라면
			System.out.println("JoinAction 로그 : 회원가입 실패");
			 request.setAttribute("msg", "회원가입에 실패했습니다. 다시 시도해주세요.");
 			request.setAttribute("path", "joinPage.do");
 			
 			forward.setPath("info.jsp");
 			forward.setRedirect(false); // 회원가입 페이지로 리다이렉트
		}
		System.out.println("JoinAction 끝");
		return forward;
	}

}
