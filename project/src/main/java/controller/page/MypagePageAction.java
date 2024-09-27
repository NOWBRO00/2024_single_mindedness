package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.member.MemberDAO;
import model.member.MemberDTO;

public class MypagePageAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("MyPagePageAction 시작");
		//로그인된 유저의 아이디로 셀렉ㅌ원
//		String member_id = (String)request.getSession().getAttribute("member_id");
		
		String login_member_id = LoginCheck.loginCheck(request);
		//데이터 로그
		System.out.println("member_id : "+login_member_id);
		ActionForward forward=new ActionForward();
		if(login_member_id.equals("")) {
			System.out.println("로그인 세션 없음");
			request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
			request.setAttribute("path", "loginPage.do");
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
			return forward;
		}
		else {
			MemberDTO memberDTO=new MemberDTO();
			memberDTO.setMember_id(login_member_id);
			memberDTO.setMember_condition("MYPAGE");
			MemberDAO memberDAO=new MemberDAO();
			memberDTO = memberDAO.selectOne(memberDTO);
			
			String[] totalAddress = memberDTO.getMember_address().split("_");
			
			memberDTO.setMember_postcode(totalAddress[0]);
			memberDTO.setMember_address(totalAddress[1]);
			memberDTO.setMember_extraAddress(totalAddress[2]);
			memberDTO.setMember_detailAddress(totalAddress[3]);
			
			request.setAttribute("member", memberDTO);
//		로그인된 유저의 정보를 가지고
//		마이페이지로 이동 forward
			forward.setPath("mypage.jsp");
			forward.setRedirect(false);
		}
		System.out.println("MyPagePageAction 끝");
		return forward;
	}
}
