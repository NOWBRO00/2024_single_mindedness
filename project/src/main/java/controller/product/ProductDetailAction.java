package controller.product;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.file.FileDAO;
import model.file.FileDTO;
import model.product.ProductDAO;
import model.product.ProductDTO;
import model.review.ReviewDAO;
import model.review.ReviewDTO;
import model.wishlist.WishlistDAO;
import model.wishlist.WishlistDTO;

public class ProductDetailAction implements Action {
	// 상품 정보
	// 상품 사진
	// 리뷰

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 예약 상세보기
		System.out.println("ProductDetailAction 시작");
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("member_id");
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		
		//데이터 로그
		System.out.println("product_num : "+product_num);
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProduct_num(product_num);
		productDTO.setProduct_condition("PRODUCT_BY_INFO");
		
		ProductDAO productDAO = new ProductDAO();
		productDTO = productDAO.selectOne(productDTO);
		
		FileDTO fileDTO = new FileDTO();
		fileDTO.setFile_product_num(product_num);
		fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
		
		FileDAO fileDAO = new FileDAO();
		ArrayList<FileDTO> fileList = fileDAO.selectAll(fileDTO);
		System.out.println(fileList);
		
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setReview_product_num(product_num);
		reviewDTO.setReview_condition("REVIEW_ALL");
		
		ReviewDAO reviewDAO = new ReviewDAO();
		ArrayList<ReviewDTO> reviewList = reviewDAO.selectAll(reviewDTO);
		
		WishlistDTO wishlistDTO = null;
		if(loginMember != null) {
			wishlistDTO = new WishlistDTO();
			wishlistDTO.setWishlist_member_id(loginMember);
			wishlistDTO.setWishlist_product_num(product_num);
			wishlistDTO.setWishlist_condition("WISHLIST_COUNT");
			
			WishlistDAO wishlistDAO = new WishlistDAO();
			wishlistDTO = wishlistDAO.selectOne(wishlistDTO);
			
			request.setAttribute("wishlist", wishlistDTO);
		}
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		if(productDTO != null) {
			System.out.println("ProductDetailAction 로그 : 상품 찾기 성공");
			request.setAttribute("product", productDTO);
			request.setAttribute("fileList", fileList);
			request.setAttribute("reviewList", reviewList);
			forward.setPath("productDetail.jsp");
		}
		else {
			System.out.println("ProductDetailAction 로그 : 상품 찾기 실패");
			request.setAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해 주세요.");
			request.setAttribute("path", "productList.do");
			forward.setPath("info.jsp");
		}
		System.out.println("ProductDetailAction 끝");
		return forward;
	}

}
