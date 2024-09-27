package controller.reservation;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.reservation.ReservationDAO;
import model.reservation.ReservationDTO;

public class ReservationDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 예약 상세 보기
		System.out.println("ReservationDetailAction 시작");
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
		
		int reservation_num = Integer.parseInt(request.getParameter("reservation_num"));
		
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setReservation_num(reservation_num);
		reservationDTO.setReservation_condition("RESERVATION_SELECTONE");
		
		ReservationDAO reservationDAO = new ReservationDAO();
		reservationDTO = reservationDAO.selectOne(reservationDTO);
		
		if(reservationDTO != null) {
			System.out.println("ReservationDetailAction 로그 : 예약 조회 성공");
			request.setAttribute("reservation", reservationDTO);
			forward.setPath("reservationDetails.jsp");
			forward.setRedirect(false);
		}
		else {
			System.out.println("ReservationDetailAction 로그 : 예약 조회 실패");
			request.setAttribute("msg", "예약 내역을 찾지 못했습니다. 다시 시도해주세요.");
			request.setAttribute("path", "reservationList.do");
			forward.setPath("info.jsp");
			forward.setRedirect(false);
		}
		System.out.println("ReservationDetailAction 끝");
		return forward;
	}

}
