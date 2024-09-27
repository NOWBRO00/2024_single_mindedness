package controller.member.old;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.member.MemberDAO;
import model.member.MemberDTO;

public class UpdateNickNameAction implements Action  {
	// 로그인된 유저의 아이디와 새로 입력받은 닉네임으로 업데이트
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("UpdateNickNameAction 시작");
		
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
		 // 새로 입력된 이름
		memberDTO.setMember_condition("NICKNAME"); // 이름을 업데이트 할 수 있도록 하는 모델과 컨트롤러의 소통요소
		
		MemberDAO memberDAO=new MemberDAO();
		
		boolean flag = memberDAO.update(memberDTO);
		
		if(flag) {
			System.out.println("UpdateNickNameAction 로그 : 성공");
			forward.setRedirect(true);
			forward.setPath("mypage.do");
		}
		else {
			System.out.println("UpdateNickNameAction 로그 : 실패");
			forward.setPath("mypage.do");
		}
		System.out.println("UpdateNameAction 끝 ");
		return forward;
	}

}
