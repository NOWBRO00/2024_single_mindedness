package controller.review;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.review.ReviewDAO;
import model.review.ReviewDTO;

public class UpdateReviewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 리뷰 수정
		System.out.println("UpdateReviewAction 시작");
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
		
		int review_star = Integer.parseInt(request.getParameter("rating"));
		String review_content = (String)request.getParameter("review_content");
		int review_product_num = Integer.parseInt(request.getParameter("review_product_num"));
		
		//데이터 로그
		System.out.println("member_id : "+login_member_id);
		System.out.println("review_star : "+review_star);
		System.out.println("review_content : "+review_content);
		System.out.println("review_product_numm : "+review_product_num);
		
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setReview_writer_id(login_member_id);
		reviewDTO.setReview_star(review_star);
		reviewDTO.setReview_content(review_content);
		reviewDTO.setReview_product_num(review_product_num);
		
		ReviewDAO reviewDAO = new ReviewDAO();
		boolean flag = reviewDAO.insert(reviewDTO);
		
		if(flag) {
			System.out.println("WriteReviewAction 로그 : 리뷰 수정 성공");
			forward.setRedirect(true);
			forward.setPath("productDetail.do?productNum="+review_product_num);
		}
		else {
			System.out.println("WriteReviewAction 로그 : 리뷰 수정 실패");
			request.setAttribute("msg", "리뷰 수정에 실패했습니다. 다시 시도해주세요.");
			request.setAttribute("path", "productDetail.do?productNum="+review_product_num);
			forward.setRedirect(false);
			forward.setPath("info.jsp");
		}
		
		System.out.println("UpdateReviewAction 끝");
		return forward;
	}

}
