package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LogoutAction 시작");
		request.getSession().removeAttribute("member_id"); // 저장된 세션 삭제
		request.getSession().removeAttribute("member_role");
		ActionForward forward=new ActionForward();
		forward.setRedirect(true); 
		forward.setPath("main.do"); // 리다이렉트 메인뷰
		System.out.println("LogoutAction 끝");
		return forward;
	}

}
