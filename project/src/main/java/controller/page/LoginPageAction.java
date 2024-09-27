package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LoginPageAction 시작");
		ActionForward forward=new ActionForward();
		forward.setPath("login.jsp"); // 로그인 페이지
		forward.setRedirect(true); // 리다이렉트로
		System.out.println("LoginPageAction 끝");
		return forward;
	}
}
