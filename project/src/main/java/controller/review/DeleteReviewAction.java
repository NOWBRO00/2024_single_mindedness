package controller.review;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.review.ReviewDAO;
import model.review.ReviewDTO;

public class DeleteReviewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 리뷰 삭제
		System.out.println("DeleteReviewAction 시작");
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
		
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		
		//데이터 로그
		System.out.println("review_num : "+review_num);
		System.out.println("product_num : "+product_num);
		
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setReview_num(review_num);
		
		ReviewDAO reviewDAO = new ReviewDAO();
		boolean flag = reviewDAO.delete(reviewDTO);
		
		if(flag) {
			System.out.println("DeleteReviewAction 로그 : 리뷰 삭제 성공");
			forward.setRedirect(true);
			forward.setPath("productDetail.do?product_num="+product_num);
		}
		else {
			System.out.println("DeleteReviewAction 로그 : 리뷰 삭제 실패");
			request.setAttribute("msg", "리뷰 삭제에 실패했습니다. 다시 시도해주세요.");
			request.setAttribute("path", "productDetail.do?product_num="+product_num);
			forward.setRedirect(false);
			forward.setPath("info.jsp");
		}
		System.out.println("DeleteReviewAction 끝");
		return forward;
	}

}
