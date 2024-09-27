package controller.reply;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.reply.ReplyDAO;
import model.reply.ReplyDTO;

public class DeleteReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("DeleteReplyAction 시작");
		
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
		
		int reply_num = Integer.parseInt(request.getParameter("reply_num"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		//데이터 로그
		System.out.println("reply_num : "+reply_num);
		System.out.println("board_num : "+board_num);
		
		ReplyDTO replyDTO=new ReplyDTO();
		replyDTO.setReply_num(reply_num);
		
		ReplyDAO replyDAO=new ReplyDAO();
		boolean flag = replyDAO.delete(replyDTO);
		
		if(flag) {
			System.out.println("DeleteReplyAction 로그 : 댓글 삭제 성공");
			forward.setPath("boardDetail.do?board_num="+board_num);
			forward.setRedirect(true);
		}
		else {
			System.out.println("DeleteReplyAction 로그 : 댓글 삭제 실패");
			request.setAttribute("msg", "댓글 삭제 실패. 다시 시도하세요.");
			request.setAttribute("path", "boardDetail.do?board_num="+board_num);
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
		}
		System.out.println("DeleteReplyAction 끝");
		return forward;
	}
}
