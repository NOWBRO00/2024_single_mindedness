package controller.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCheck {
	public static String loginCheck(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("member_id");
		
		if(session == null || loginMember == null) {
			loginMember = "";
		}
		
		return loginMember;
	}
}
