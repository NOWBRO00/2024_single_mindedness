package controller.reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.payment.PaymentDAO;
import model.payment.PaymentDTO;
import model.reservation.ReservationDAO;
import model.reservation.ReservationDTO;

public class MakeReservationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 예약
		System.out.println("MakeReservationAction 시작");
		
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
		
		String merchant_uid = request.getParameter("merchant_uid");
		String regDate = request.getParameter("reservation_registrarion_date");
		System.out.println(regDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		 // 문자열을 java.util.Date로 변환
        Date reservationDate = null;
		try {
			reservationDate = formatter.parse(regDate);
		} catch (ParseException e) {
			System.out.println("형변환 실패");
			e.printStackTrace();
		}

		//데이터 로그
		System.out.println("merchant_uid : "+merchant_uid);
		System.out.println("reservation_registrarion_date : "+regDate);
		
		// 결제 번호 가져오기
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setMerchant_uid(merchant_uid);
		paymentDTO.setPayment_condition("SELECT_BY_MERCHANT_UID");
		
		PaymentDAO paymentDAO = new PaymentDAO();
		paymentDTO = paymentDAO.selectOne(paymentDTO);
		
		
		ReservationDTO reservationDTO = new ReservationDTO();
		//reservationDTO.setMerchant_uid(merchant_uid);
		reservationDTO.setReservation_registration_date(reservationDate);
		reservationDTO.setReservation_payment_num(paymentDTO.getPayment_num());
		
		ReservationDAO reservationDAO = new ReservationDAO();
		boolean flag = reservationDAO.insert(reservationDTO);
		
		if(flag) {
			System.out.println("MakeReservationAction 로그 : 예약 성공");
			reservationDTO.setReservation_condition("RESERVATION_LAST_NUM");
			reservationDTO = reservationDAO.selectOne(reservationDTO);
			forward.setPath("reservationDetail.do?reservation_num="+reservationDTO.getReservation_num());
//			forward.setPath("main.do");
			forward.setRedirect(true);
		}
		else {
			System.out.println("MakeReservationAction 로그 : 예약 실패");
			request.setAttribute("msg", "예약에 실패 했습니다. 다시 시도해주세요.");
			request.setAttribute("path", "main.do");
			forward.setPath("info.jsp");
		}
		
		System.out.println("MakeReservationAction 끝");
		return forward;
	}
}


// 결제 번호, 예약 날짜, 예약 상태
// 


// 예약이 완료 되었습니다 info.jsp
// 예약 상세 페이지