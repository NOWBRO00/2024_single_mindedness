package controller.claim;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.claim.ClaimDAO;
import model.claim.ClaimDTO;

public class ReportAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 신고
		System.out.println("ReportAction 시작");
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
		
		String report_content = request.getParameter("report_content");
		String claim_category = request.getParameter("claim_category");
		int claim_content_num = Integer.parseInt(request.getParameter("claim_content_num"));
		String claim_target_member_id = request.getParameter("claim_target_member_id");
		String claim_reporter_id = request.getParameter(login_member_id);
		
		//데이터 로그
		System.out.println("report_content"+report_content);
		System.out.println("claim_category"+claim_category);
		System.out.println("claim_content_num"+claim_content_num);
		System.out.println("claim_target_member_id"+claim_target_member_id);
		System.out.println("claim_reporter_id"+claim_reporter_id);
		
		ClaimDTO claimDTO = new ClaimDTO();
		if(report_content.equals("board")) {
			claimDTO.setClaim_board_num(claim_content_num);
			claimDTO.setClaim_condition("INSERT_CLAIM_BOARD");
		}
		else {
			claimDTO.setClaim_reply_num(claim_content_num);
			claimDTO.setClaim_condition("INSERT_CLAIM_REPLY");
		}
		claimDTO.setClaim_category(claim_category);
		claimDTO.setClaim_target_member_id(claim_target_member_id);
		claimDTO.setClaim_reporter_id(claim_reporter_id);
		
		ClaimDAO claimDAO = new ClaimDAO();
		boolean flag = claimDAO.insert(claimDTO);
		
		request.setAttribute("path", "boardListPage.do");
		if(flag) {
			request.setAttribute("msg", "신고가 처리가 완료되었습니다. 감사합니다.");
			forward.setPath("info.jsp");
			forward.setRedirect(false);
		}
		else {
			request.setAttribute("msg", "신고가 처리에 실패했습니다. 다시 시도해 주세요.");
			forward.setPath("info.jsp");
			forward.setRedirect(false);
		}
		
		System.out.println("ReportAction 끝");
		return forward;
	}

}
