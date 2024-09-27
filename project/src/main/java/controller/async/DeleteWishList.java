package controller.async;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.wishlist.WishlistDAO;
import model.wishlist.WishlistDTO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteWishList")
public class DeleteWishList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteWishList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET 요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청 도착");
		// 위시리스트 삭제
		System.out.println("DeleteWishList 시작");

		// V에게 삭제할 위시리스트 번호를 받아온다.
		int wishlist_num = Integer.parseInt(request.getParameter("wishlist_num"));

		// 데이터 로그
		System.out.println("DeleteWishList wishlist_num : " + wishlist_num);

		// M에게 전달하기 위한 DTO와 전달하고 결과를 받을 DAO 객체를 new 생성해준다.
		WishlistDTO wishlistDTO = new WishlistDTO();
		WishlistDAO wishlistDAO = new WishlistDAO();

		// DTO 객체에 위시리스트 번호를 담아준다.
		wishlistDTO.setWishlist_num(wishlist_num);
		// M에게 DTO객체를 전달해주고, boolean 타입으로 반환받는다.
		boolean flag = wishlistDAO.delete(wishlistDTO);

		// 응답을 반환하기 위한 객체 생성
		PrintWriter out = response.getWriter();
		if(flag) { // 만약 true 반환받는다면 
			// 성공 응답 반환
			System.out.println("DeleteWishList 성공 반환");
			out.print(flag);
		}
		else { // 만약 false 반환받는다면
			// 실패 응답 반환
			System.out.println("DeleteWishList 실패 반환");
			out.print(flag);
		}
		
		System.out.println("응답값: " + flag);
		System.out.println("DeleteWishList 끝");

	}

}
