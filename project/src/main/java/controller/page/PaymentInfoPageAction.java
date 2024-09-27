package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.file.FileDAO;
import model.file.FileDTO;
import model.member.MemberDAO;
import model.member.MemberDTO;
import model.product.ProductDAO;
import model.product.ProductDTO;

public class PaymentInfoPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("PaymentInfoPageAction 시작");
		
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
		
		// 상품명 카테고리 사용예정일 예약자명 전화번호 결제 금액
		String reservation_date = request.getParameter("reservation_date");
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProduct_num(product_num);
		productDTO.setProduct_condition("PRODUCT_BY_INFO");
		
		ProductDAO productDAO = new ProductDAO();
		productDTO = productDAO.selectOne(productDTO);
		
		if(productDTO == null) {
			request.setAttribute("msg", "상품을 찾을 수 없습니다. 다시 시도해주세요.");
			request.setAttribute("path", "main.do");
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
			return forward;
		}
		
		FileDTO fileDTO = new FileDTO();
		fileDTO.setFile_product_num(product_num);
		fileDTO.setFile_condition("PRODUCT_FILE_SELECTALL");
		
		FileDAO fileDAO = new FileDAO();
		ArrayList<FileDTO> fileList = fileDAO.selectAll(fileDTO);
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMember_id(login_member_id);
		memberDTO.setMember_condition("CHECK_MEMBER_ID");
		
		MemberDAO memberDAO = new MemberDAO();
		memberDTO = memberDAO.selectOne(memberDTO);
		
		if(memberDTO == null) {
			request.setAttribute("msg", "사용자 정보를 찾을 수 없습니다. 다시 시도해주세요.");
			request.setAttribute("path", "main.do");
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
			return forward;
		}
		
		request.setAttribute("reservation_date", reservation_date);
		request.setAttribute("product", productDTO);
		request.setAttribute("fileList", fileList);
		request.setAttribute("member", memberDTO);
		System.out.println("70 product_name: "+productDTO.getProduct_name());
		System.out.println("71 member_name : "+memberDTO.getMember_name());
		forward.setPath("reservation.jsp");
		forward.setRedirect(false);
		
		System.out.println("PaymentInfoPageAction 끝");
		return forward;
	}

}
