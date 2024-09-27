package controller.member.old;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.member.MemberDAO;
import model.member.MemberDTO;

public class UpdateAddressAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("UpdateAddressAction 시작");

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
		
		String postcode=request.getParameter("postcode"); // 우편번호
		String address=request.getParameter("address"); // 주소
		String extraAddress=request.getParameter("extraAddress"); // 주가 주소
		String detailAddress=request.getParameter("detailAddress"); // 상세 주소
		
		//데이터 로그
		System.out.println("member_id : "+login_member_id);
		System.out.println("address : "+address);
		System.out.println("extraAddress : "+extraAddress);
		System.out.println("detailAddress : "+detailAddress);
		// 전체 주소 
		String totalAddress ="("+postcode+")"+address+extraAddress+detailAddress;
		System.out.println("totalAddress : "+totalAddress);
		
		boolean flag = false;
		
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setMember_id(login_member_id);
			memberDTO.setMember_address(totalAddress); // 새로 입력된 주소
			memberDTO.setMember_condition("ADDRESS"); // 이름을 업데이트 할 수 있도록 하는 모델과 컨트롤러의 소통요소
			
			MemberDAO memberDAO=new MemberDAO();
			
			flag = memberDAO.update(memberDTO);
			
			if(flag) {
				System.out.println("UpdateAddressAction 로그 : 성공");
				forward.setRedirect(true);
				forward.setPath("mypage.do");
			}
			else {
				System.out.println("UpdateAddressAction 로그 : 실패");
				forward.setPath("mypage.do");
			}
		System.out.println("UpdateAddressAction 끝");
		return forward;
	}

}
