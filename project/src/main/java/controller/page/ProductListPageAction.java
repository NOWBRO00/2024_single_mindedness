package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.ProductDAO;
import model.product.ProductDTO;

public class ProductListPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 상품 목록 페이지
		System.out.println("ProductListPageAction 시작");

		// V에서 받아온 파라미터
		String product_searchKeyword = request.getParameter("product_searchKeyword"); // 검색어
		String product_location = request.getParameter("product_location"); // 상품 장소 (바다,민물)
		String product_category = request.getParameter("product_category"); // 상품 유형 (낚시배, 낚시터, 바다, 민물)
		String product_searchContent = request.getParameter("product_searchContent"); // 기본, 좋아요, 최신순 정렬


		// 페이지 번호를 V에서 받아와 M에게 데이터 요청
		String product_page = request.getParameter("currentPage");
		System.out.println("productPageAction product_page : " + product_page);

		//데이터 로그
		System.out.println("product_searchKeyword : "+product_searchKeyword);
		System.out.println("product_location : " + product_location);
		System.out.println("product_category : " + product_category);
		System.out.println("product_searchContent : " + product_searchContent);

		ProductDTO productDTO = new ProductDTO();

		// 만약 받아온 페이지 번호가 null 이라면 기본값 1
		int product_page_num;

		if(product_page != null) {	
			System.out.println("productPageAction 받아온 페이지 번호 null이 아닌 경우");
			// 받아온 페이지 번호가 null이 아니라면
			// int 타입으로 변환
			product_page_num = Integer.parseInt(product_page);
			// M에게 전달하기 위해 DTO에 담아준다.
			productDTO.setProduct_page_num(product_page_num);
		}
		else {
			System.out.println("productPageAction 받아온 페이지 번호 null인 경우");
			// M에게 전달하기 위해 DTO에 담아준다.
			product_page_num =1;
			productDTO.setProduct_page_num(product_page_num);
		}

		// 전체 페이지 개수를 받아오기 위해 객체 생성
		ProductDTO page_count = new ProductDTO();
		ProductDAO productDAO = new ProductDAO();

		page_count.setProduct_page_num(product_page_num);
		page_count.setProduct_condition("PRODUCT_PAGE_COUNT");
		page_count = productDAO.selectOne(page_count);
		// int 타입 변수에 받아온 값을 넣어준다.
		int product_page_count = page_count.getProduct_total_page();

		if (product_page_count < 1) {
			product_page_count = 1; // 최소 페이지 수를 1로 설정
		}


		// 검색 키워드가 없을 경우
		if (product_searchKeyword == null || product_searchKeyword.isEmpty()) {
			// 검색어가 없는 경우
			if ((product_location == null || product_location.isEmpty()) && 
					(product_category == null || product_category.isEmpty())) {
				System.out.println("productPageAction 로그: 필터링 조건이 없는 경우. 기본 전체 출력");
				productDTO.setProduct_condition("PRODUCT_BY_ALL");
			} else {
				// 필터링 조건이 있을 경우
				System.out.println("productPageAction 로그: 필터링 조건이 있는 경우. 필터링 출력");
				productDTO.setProduct_location(product_location);
				productDTO.setProduct_category(product_category);
				productDTO.setProduct_condition("PRODUCT_BY_FILTERING");
			}
		}else {
			// 검색어가 있는 경우
			System.out.println("productPageAction 로그: 검색어 조건이 있는 경우");

			if(product_searchContent.equals("PRODUCT_BY_RATING")) {
				System.out.println("productPageAction 로그: 별점순 출력");
				//productDTO.setProduct_page_num(page_num);
				productDTO.setProduct_page_num(1);
				productDTO.setProduct_condition("PRODUCT_BY_RATING");
			}
			else if(product_searchContent.equals("PRODUCT_BY_RESERVATIONS")) {
				System.out.println("productPageAction 로그: 예약(결제)많은 순 출력");
				//productDTO.setProduct_page_num(page_num);
				productDTO.setProduct_page_num(1);
				productDTO.setProduct_condition("PRODUCT_BY_RESERVATIONS");
			}
			else if(product_searchContent.equals("PRODUCT_BY_WISHLIST")) {
				System.out.println("productPageAction 로그: 찜 많은 순 출력");
				//productDTO.setProduct_page_num(page_num);
				productDTO.setProduct_page_num(1);
				productDTO.setProduct_condition("PRODUCT_BY_WISHLIST");
			}
			else if(product_searchContent.equals("PRODUCT_BY_KEYWORD")) {
				System.out.println("productPageAction 로그: 검색하여 출력");
				productDTO.setProduct_page_num(1);
				productDTO.setProduct_searchKeyword(product_searchKeyword);
				productDTO.setProduct_condition("PRODUCT_BY_KEYWORD");
			}
			else {
				System.out.println("productPageAction 로그: 전체 출력");
				productDTO.setProduct_condition("PRODUCT_BY_ALL");
			}
		}


		ArrayList<ProductDTO> productList = productDAO.selectAll(productDTO);

		request.setAttribute("productList", productList);
		request.setAttribute("product_page_count", product_page_count); // 게시판 페이지 개수
		request.setAttribute("currentPage", product_page_num); // 게시판 현재 페이지

		ActionForward forward = new ActionForward();
		forward.setPath("productList.jsp");
		forward.setRedirect(false);

		System.out.println("ProductListPageAction 끝");
		return forward;
	}

}
