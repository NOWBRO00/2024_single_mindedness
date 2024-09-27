package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.wishlist.WishlistDAO;
import model.wishlist.WishlistDTO;

public class WishListPageAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 위시리스트 목록 페이지
		System.out.println("WishListPageAction 시작");
		
		// 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게도 전달 필요)
		String login_member_id = LoginCheck.loginCheck(request);
		
		// 데이터 로그
		System.out.println("member_id : "+login_member_id);
		
		// 데이터를 보내기 위해 ActionForward 객체 new 생성
		ActionForward forward = new ActionForward();
		
		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("로그인 세션 없음");
			// 로그인 안내 후 login 페이지로 이동시킨다
			request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
			request.setAttribute("path", "loginPage.do");
			
			// 데이터를 보낼 경로
			forward.setPath("info.jsp");
			// msg와 path를 보내야 하므로 포워드방식
			forward.setRedirect(false);
			return forward;
		}
		else { // 만약 로그인 상태라면
			System.out.println("WishListPageAction 로그인 상태 시작");

			// M에게 데이터를 담을 용도인 DTO와, 전달하기 위한 DAO 객제를 new로 생성한다.
			WishlistDTO wishlistDTO = new WishlistDTO();
			WishlistDAO wishlistDAO = new WishlistDAO();
			
			// 세션에서 받아온 id 정보를 M에게 전달하기 위해 DTO 객체에 담아준다.
			wishlistDTO.setWishlist_member_id(login_member_id);
			
			// 페이지 번호를 V에서 받아와 M에게 데이터 요청
			String wishlist_page = request.getParameter("current_page");
			System.out.println("WishListPageAction wishlist_page : " + wishlist_page);

			// 만약 받아온 페이지 번호가 null 이라면 기본값 1
			int wishlist_page_num;
			
			if(wishlist_page != null) {	
				System.out.println("WishListPageAction 받아온 페이지 번호 null이 아닌 경우");
				// 받아온 페이지 번호가 null이 아니라면
				// int 타입으로 변환
				wishlist_page_num = Integer.parseInt(wishlist_page);
				// M에게 전달하기 위해 DTO에 담아준다.
				wishlistDTO.setWishlist_page_num(wishlist_page_num);
			}
			else {
				System.out.println("WishListPageAction 받아온 페이지 번호 null인 경우");
				// M에게 전달하기 위해 DTO에 담아준다.
				wishlist_page_num =1;
				wishlistDTO.setWishlist_page_num(wishlist_page_num);
			}
			
			System.out.println("WishListPageAction if문 이후 wishlist_page_num : " + wishlistDTO.getWishlist_page_num());

			
			// 위시리스트 전체 목록
			// M에게 데이터를 보내주고, 결과를 ArrayList로 반환받는다.
			ArrayList<WishlistDTO> wishlist = new ArrayList<WishlistDTO>();
			wishlist = wishlistDAO.selectAll(wishlistDTO);
			
			System.out.println("로그 wishlist AL : " + wishlist);
			
			// 위시리스트 개수
			// M에게 데이터를 보내주고, 결과를 DTO로 반환받는다.
			wishlistDTO.setWishlist_condition("WISHLIST_COUNT");
			wishlistDTO = wishlistDAO.selectOne(wishlistDTO);
			//int 타입 변수에 받아온 값을 넣어준다.
			int wishlist_count = wishlistDTO.getWishlist_count();
			
			// 위시리스트 페이지 개수
			WishlistDTO page_count = new WishlistDTO();
			page_count.setWishlist_member_id(login_member_id); // 회원 아이디 (M전달)
			page_count.setWishlist_condition("WISHLIST_PAGE_COUNT");
			page_count = wishlistDAO.selectOne(page_count);
			// int 타입 변수에 받아온 값을 넣어준다.
			int wishlist_page_count = page_count.getWishlist_total_page();
			
			if (wishlist_page_count < 1) {
			    wishlist_page_count = 1; // 최소 페이지 수를 1로 설정
			}
			
			// V에게 전달해주기 위해 request 객체에 데이터를 저장한다.
			request.setAttribute("wishlist", wishlist); // 위시리스트 목록
			request.setAttribute("wishlist_count", wishlist_count); // 위시리스트 개수
			request.setAttribute("wishlist_page_count", wishlist_page_count); // 위시리스트 페이지 개수
			request.setAttribute("current_page", wishlist_page_num); // 위시리스트 현재 페이지
			
			// 데이터 로그
			System.out.println("wishlist : "+request.getAttribute("wishlist"));
			System.out.println("wishlist_count : "+request.getAttribute("wishlist_count"));
			System.out.println("wishlist_page_count : "+request.getAttribute("wishlist_page_count"));
			System.out.println("current_page : "+request.getAttribute("current_page"));
		
			
			// 어디로 보내는지 경로를 작성한다. (V파일 == jsp 파일)
			forward.setPath("wishList.jsp");
			// 보낼 데이터가 있다면 포워드, 없다면 리다이렉트 방식으로 전달한다.
			forward.setRedirect(false); // 포워드
		}
		
		System.out.println("WishListPagetAction 로그 : else문 종료");
		return forward;
	}

}
