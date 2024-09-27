package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import model.member.MemberDAO;
import model.member.MemberDTO;

public class LoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LoginAction 시작");
		// 아이디 비밀번호
		String member_id=request.getParameter("member_id");
		String member_password=request.getParameter("member_password");
		
		//데이터 로그
		System.out.println("member_id : "+member_id);
		System.out.println("member_pw : "+member_password);
		
		MemberDAO memberDAO=new MemberDAO();
		MemberDTO memberDTO=new MemberDTO();
		
		memberDTO.setMember_id(member_id); // 아이디
		memberDTO.setMember_password(member_password); // 비밀번호
		memberDTO.setMember_condition("LOGIN"); // 로그인 selectOne
		
		memberDTO = memberDAO.selectOne(memberDTO); 
		
		ActionForward forward=new ActionForward();
		if(memberDTO != null) { // 성공이라면
			System.out.println("LoginAction 로그 : 성공");
			HttpSession session=request.getSession(); // 세션에 로그인 멤버 set
			session.setAttribute("member_id", memberDTO.getMember_id()); // pk 값
			session.setAttribute("member_role", memberDTO.getMember_role()); // 일반 유저인지, 관리자인지, 사장님인지
			forward.setRedirect(false); // 세션을 전달해야하기 때문에 forward
			forward.setPath("main.do"); // 일반 유저라면 ?메인 페이지로 이동
		}
		else { // 로그인 실패라면
			System.out.println("LoginAction 로그 : 실패");
			request.setAttribute("msg", "로그인에 실패했습니다. 다시 시도해주세요.");
			request.setAttribute("path", "loginPage.do");
			
			forward.setPath("info.jsp");
			forward.setRedirect(false); // 로그인페이지로 리다이렉트
		}
		System.out.println("LoginAction 끝");
		return forward;
	}

}
