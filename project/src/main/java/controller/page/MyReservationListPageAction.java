package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.reservation.ReservationDAO;
import model.reservation.ReservationDTO;

public class MyReservationListPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReservationListPageAction 시작");
		
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
		
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setReservation_member_id(login_member_id);
		
		ReservationDAO reservationDAO = new ReservationDAO();
		ArrayList<ReservationDTO> myReservationList = reservationDAO.selectAll(reservationDTO);
		
		request.setAttribute("myReservationList", myReservationList);
		
		forward.setPath("myReservationList.jsp");
		forward.setRedirect(false);
		
		System.out.println("ReservationListPageAction 끝");
		return forward;
	}

}
