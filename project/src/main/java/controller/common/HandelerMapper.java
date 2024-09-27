package controller.common;

import java.util.HashMap;
import java.util.Map;

import controller.board.BoardDetailAction;
import controller.board.DeleteBoardAction;
import controller.board.UpdateBoardAction;
import controller.board.WriteBoardAction;
import controller.claim.ReportAction;
import controller.member.DeleteMemberAction;
import controller.member.JoinAction;
import controller.member.LoginAction;
import controller.member.LogoutAction;
import controller.member.UpdateMypageAction;
import controller.member.UpdatePasswordAction;
import controller.member.UpdateProfileAction;
import controller.page.BoardListPageAction;
import controller.page.JoinPageAction;
import controller.page.LoginPageAction;
import controller.page.MainPageAction;
import controller.page.MyBoardListPageAction;
import controller.page.MyReservationListPageAction;
import controller.page.MypagePageAction;
import controller.page.PaymentInfoPageAction;
import controller.page.ProductListPageAction;
import controller.page.UpdateBoardPageAction;
import controller.page.WishListPageAction;
import controller.page.WriteBoardPageAction;
import controller.payment.CancelPaymentAction;
import controller.payment.UpdatePaymentAction;
import controller.product.ProductDetailAction;
import controller.reply.DeleteReplyAction;
import controller.reply.WriteReplyAction;
import controller.reservation.CancelReservationAction;
import controller.reservation.MakeReservationAction;
import controller.reservation.ReservationDetailAction;
import controller.reservation.UpdateReservationAction;
import controller.review.DeleteReviewAction;
import controller.review.UpdateReviewAction;
import controller.review.WriteReviewAction;
import controller.wishlist.AddWishListAction;

public class HandelerMapper {
	// Action을 맵핑할 Map
	private Map<String, Action> mapper;
	
	public HandelerMapper() {
		// 멤버변수 초기화
		this.mapper = new HashMap<String, Action>();
		
		// Action Mapping
		
		// member
		this.mapper.put("/join.do", new JoinAction()); // 회원가입 액션
		this.mapper.put("/login.do", new LoginAction()); // 로그인 액션
		this.mapper.put("/logout.do", new LogoutAction()); // 로그아웃 액션
		//this.mapper.put("/updateAddress.do", new UpdateAddressAction()); // 주소변경 액션
		this.mapper.put("/updateMypage.do", new UpdateMypageAction()); // 이름 / 닉네임 변경 액션
//		this.mapper.put("/updateNickName.do", new UpdateNickNameAction()); // 닉네임변경 액션
		this.mapper.put("/updatePassword.do", new UpdatePasswordAction()); // 비밀번호변경 액션
		this.mapper.put("/updateProfile.do", new UpdateProfileAction()); // 프로필사진변경 액션
		this.mapper.put("/deleteMember.do", new DeleteMemberAction()); // 회원탈퇴 액션
		
		// board
		this.mapper.put("/writeBoard.do", new WriteBoardAction()); // 게시글 작성 액션
		this.mapper.put("/boardDetail.do", new BoardDetailAction()); // 게시글 상세 보기 액션
		this.mapper.put("/updateBoard.do",new UpdateBoardAction()); // 게시글 수정 액션
		this.mapper.put("/deleteBoard.do", new DeleteBoardAction()); // 게시글 삭제 액션
		
		// reply
		this.mapper.put("/writeReply.do", new WriteReplyAction()); // 댓글 작성 액션
		this.mapper.put("/deleteReply.do", new DeleteReplyAction()); // 댓글 삭제 액션
		
		// product
		this.mapper.put("/productDetail.do", new ProductDetailAction()); // 상품 상세 보기 액션
		
		//review
		this.mapper.put("/writeReview.do", new WriteReviewAction()); // 리뷰 작성 액션
		this.mapper.put("/updateReview.do", new UpdateReviewAction()); // 리뷰 수정 액션
		this.mapper.put("/deleteReview.do", new DeleteReviewAction()); // 리뷰 삭제 액션
		
		// wishlist
		this.mapper.put("/addWishList.do", new AddWishListAction()); // 위시리스트 추가 액션
		
		// payment
		this.mapper.put("/cancelPayment.do", new CancelPaymentAction()); // 결제 취소 액션
		this.mapper.put("/updatePayment.do", new UpdatePaymentAction()); // 결제 상태 업데이트 액션
		
		// reservation
		this.mapper.put("/makeReservation.do", new MakeReservationAction()); // 예약 액션
		this.mapper.put("/updateReservation.do", new UpdateReservationAction()); // 예약 변경 액션
		this.mapper.put("/cancelReservation.do", new CancelReservationAction()); // 예약 취소 액션
		this.mapper.put("/reservationDetail.do", new ReservationDetailAction()); // 예약 상세 보기 액션
		
		// claim
		this.mapper.put("/report.do", new ReportAction()); // 신고 액션

		// 페이지 이동 매핑
		this.mapper.put("/main.do", new MainPageAction());	// 메인 페이지 액션
		this.mapper.put("/joinPage.do", new JoinPageAction()); // 회원가입 페이지 액션
		this.mapper.put("/loginPage.do", new LoginPageAction()); // 로그인 페이지 액션
		this.mapper.put("/mypage.do", new MypagePageAction()); // 마이페이지 페이지 액션
		this.mapper.put("/boardListPage.do", new BoardListPageAction()); // 글목록 페이지 액션
		this.mapper.put("/writeBoardPage.do", new WriteBoardPageAction()); // 글 작성 페이지 액션
		this.mapper.put("/updateBoardPage.do", new UpdateBoardPageAction()); // 글 수정 페이지 액션
		this.mapper.put("/myBoardListPage.do", new MyBoardListPageAction()); // 내가 쓴 글목록 페이지 액션
		this.mapper.put("/productListPage.do", new ProductListPageAction()); // 상품 목록 페이지 액션
		this.mapper.put("/wishListPage.do", new WishListPageAction()); // 위시리스트 목록 페이지 액션
		this.mapper.put("/myReservationListPage.do", new MyReservationListPageAction()); // 예약 목록 페이지 액션
		this.mapper.put("/paymentInfoPage.do", new PaymentInfoPageAction()); // 결제전 결제 정보 확인 페이지 액션
	}
	
	// 요청을 파라미터로 받고 
	// 파라미터(key)에 해당하는 액션 반환
	public Action getAction(String command) {
		return this.mapper.get(command);
	}
}
