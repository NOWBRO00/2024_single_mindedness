package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.member.MemberDAO;
import model.member.MemberDTO;


public class DeleteMemberAction implements Action  {
	// 로그인된 유저의 아이디로
	// 회원 탈퇴
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("DeleteMemberAction 시작");
		
		String login_member_id = LoginCheck.loginCheck(request);
		ActionForward forward=new ActionForward();
		if(login_member_id.equals("")) {
			System.out.println("로그인 세션 없음");
			request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
			request.setAttribute("path", "loginPage.do");
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
			return forward;
		}
		
		//데이터 로그
		System.out.println("member_id : "+login_member_id);
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMember_id(login_member_id);
		MemberDAO memberDAO=new MemberDAO();
		boolean flag=memberDAO.delete(memberDTO);
		
		forward.setRedirect(true);
		if(flag) {
			System.out.println("DeleteMemberAction 로그 : 성공");
			HttpSession session = request.getSession();
			session.removeAttribute("member_id");
			session.removeAttribute("role");
			forward.setPath("main.do");
		}
		else {
			System.out.println("DeleteMemberAction 로그 : 실패");
			request.setAttribute("msg", "회원 탈퇴에 실패했습니다.");
 			request.setAttribute("path", "mypagePage.do");
 			
 			forward.setPath("info.jsp");
 			forward.setRedirect(false);
		}
		return forward;
	}

}
