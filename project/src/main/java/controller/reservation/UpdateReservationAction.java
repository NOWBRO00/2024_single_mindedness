package controller.reservation;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateReservationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 예약 변경
		System.out.println("UpdateReservationAction 시작");
		
		
		
		System.out.println("UpdateReservationAction 끝");
		return null;
	}

}
// 변경 내용 가지고 update
// 성공시 예약 상세
// 

/*
	예약 변경 페이지 필요 ?
	
	예약 변경
	변경 내용 받아야됨
	
	원 예약을 취소로 바꾸고 환불
	변경 내용을 가지고 재 결제 / 재 예약
	
	현재 상품은 날짜별로 가격이 다르지 않음
	재결제가 필요해보이지 않음
	현 예약만 취소로 바꾸고
	재 예약 ?
	아니면
	현 예약에 날짜를 업데이트 ?
*/