package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JoinPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("JoinPageAction 시작");
		ActionForward forward=new ActionForward();
		forward.setPath("join.jsp");
		forward.setRedirect(true);
		System.out.println("JoinPageAction 끝");
		return forward;
	}

}
