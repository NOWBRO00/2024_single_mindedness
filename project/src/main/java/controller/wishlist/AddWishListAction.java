package controller.wishlist;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.wishlist.WishlistDAO;
import model.wishlist.WishlistDTO;

public class AddWishListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 위시리스트 추가
		System.out.println("AddWishListAction 시작");

		// // 세션에서 로그인 정보를 가져와 DTO의 member_id에 담아준다. (M에게도 전달 필요)
		String login_member_id = LoginCheck.loginCheck(request);

		// 데이터 로그
		System.out.println("AddWishListAction member_id : "+login_member_id);

		// 데이터를 보내기 위해 ActionForward 객체 new 생성
		ActionForward forward = new ActionForward();

		if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
			System.out.println("AddWishListAction 로그인 세션 없음");
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
			System.out.println("AddWishListAction 로그인 상태");
			// M에게 데이터를 담을 용도인 DTO와, 전달하기 위한 DAO 객제를 new로 생성한다.
			WishlistDTO wishlistDTO = new WishlistDTO();
			WishlistDAO wishlistDAO = new WishlistDAO();

			// 로그인한 회원의 ID를 DTO 객체에 넣어준다.
			wishlistDTO.setWishlist_member_id(login_member_id);
			// V에게 받은 상품 번호를 DTO 객체에 넣어준다.
			int wishlist_product_num = Integer.parseInt(request.getParameter("wishlist_product_num"));
			wishlistDTO.setWishlist_product_num(wishlist_product_num);

			// 중복 확인을 위해 M에게 데이터를 보내, 해당 회원의 위시리스트를 반환받는다.
			ArrayList<WishlistDTO> wishlist = wishlistDAO.selectAll(wishlistDTO);

			// 위시리스트 중 해당 상품 번호가 있는지 확인
			for (WishlistDTO item : wishlist) {
				if (item.getWishlist_product_num() == wishlist_product_num) {
					// 원하는 상품 번호를 찾았을 때 처리
					System.out.println("AddWishListAction 찾은 상품 번호: " + item.getWishlist_product_num());
					// 실패 안내
					request.setAttribute("msg", "이미 추가된 상품입니다.");
					// 상품 상세페이지로 이동
					request.setAttribute("path", "productDetail.do?product_num="+wishlist_product_num);
					// 어디로 보내는지 경로를 작성한다
					// 보낼 데이터가 있다면 포워드, 없다면 리다이렉트 방식으로 전달한다.
					forward.setPath("info.jsp");
					forward.setRedirect(false); // msg와 path 보내야 하므로 포워드
					return forward;
				}
				System.out.println("AddWishListAction 위시리스트 중복 상품번호 찾기 종료");
			}


			// M에게 받은 위시리스트 중 없다면 insert 진행
			// M에게 DTO 객체를 보내 insert 진행하고, 결과를 boolean 타입으로 반환받는다.
			boolean flag = wishlistDAO.insert(wishlistDTO);

			if(flag) {// 만약 true 반환받았다면 
				System.out.println("AddWishListAction insert 성공");
				// 상품 상세페이지로 이동
				forward.setPath("productDetail.do?product_num="+wishlist_product_num);
				forward.setRedirect(true);
			}
			else { // 만약 false 반환받았다면
				System.out.println("AddWishListAction insert 실패");
				// 실패 안내
				request.setAttribute("msg", "위시리스트 추가에 실패했습니다. 다시 시도해주세요.");
				// 상품 상세페이지로 이동
				request.setAttribute("path", "productDetail.do?product_num="+wishlist_product_num);

				// 어디로 보내는지 경로를 작성한다
				// 보낼 데이터가 있다면 포워드, 없다면 리다이렉트 방식으로 전달한다.
				forward.setPath("info.jsp");
				forward.setRedirect(false); // msg와 path 보내야 하므로 포워드
			}
		}

		System.out.println("AddWishListAction 끝");
		return forward;
	}

}
