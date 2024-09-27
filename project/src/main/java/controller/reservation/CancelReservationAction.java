package controller.reservation;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.payment.PaymentDAO;
import model.payment.PaymentDTO;
import model.reservation.ReservationDAO;
import model.reservation.ReservationDTO;

public class CancelReservationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 예약 취소
		System.out.println("CancelReservationAction 시작");
		
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
		//String merchant_uid = request.getParameter("merchant_uid");
		
		
		//데이터 로그
		System.out.println("reservation_num : "+reservation_num);
		
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setReservation_num(reservation_num);
		reservationDTO.setReservation_condition("RESERVATION_SELECTONE");
		
		ReservationDAO reservationDAO = new ReservationDAO();
		reservationDTO = reservationDAO.selectOne(reservationDTO);
		System.out.println(reservationDTO);
		boolean flag = false;
		if(reservationDTO != null) {
			reservationDTO.setReservation_status("예약취소");
			flag = reservationDAO.update(reservationDTO);
		}
		else {
			System.out.println("예약 내역 없음");
			request.setAttribute("msg", "예약내역을 찾을 수 없습니다. 다시 시도해주세요.");
			request.setAttribute("path", "myReservationListPage.do");
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("결제 번호 : "+reservationDTO.getReservation_payment_num());
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setPayment_num(reservationDTO.getReservation_payment_num());
		paymentDTO.setPayment_condition("SELECT_BY_PAYMENT_NUM");
		
		PaymentDAO paymentDAO = new PaymentDAO();
		paymentDTO = paymentDAO.selectOne(paymentDTO);
		System.out.println(paymentDTO);
		
		if(flag) {
			System.out.println("CancelReservationAction 로그 : 예약 취소 성공");
			forward.setPath("cancelPayment.do?merchant_uid="+paymentDTO.getMerchant_uid());
			forward.setRedirect(true);
		}
		else {
			System.out.println("CancelReservationAction 로그 : 예약 취소 실패");
			request.setAttribute("msg", "예약 취소에 실패했습니다. 다시 시도해주세요.");
			request.setAttribute("path", "main.do");
			forward.setPath("info.jsp");
			forward.setRedirect(false);
		}
		
		System.out.println("CancelReservationAction 끝");
		return forward;
	}

}
// 예약 취소로 업데이트
// 결제 취소