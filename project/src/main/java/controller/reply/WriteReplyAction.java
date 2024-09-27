package controller.reply;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.reply.ReplyDAO;
import model.reply.ReplyDTO;

public class WriteReplyAction implements Action  {
	// 로그인 확인
	// 댓글 내용
	// 댓글 작성자
//	insert
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("WriteReplyAction 시작");
		
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
		
		int board_num=Integer.parseInt(request.getParameter("board_num"));
		String reply_content=(String)request.getParameter("reply_content");
		//int reply_private=Integer.parseInt(request.getParameter("reply_private"));
		
		//데이터 로그
		System.out.println("member_id : "+login_member_id);
		System.out.println("board_num : "+board_num);
		System.out.println("reply_content : "+reply_content);
		//System.out.println("reply_private : "+reply_private);
		
		ReplyDTO replyDTO=new ReplyDTO();
		replyDTO.setReply_writer_id(login_member_id);
		replyDTO.setReply_board_num(board_num);
		replyDTO.setReply_content(reply_content);
		replyDTO.setReply_private(1);
		
		ReplyDAO replyDAO=new ReplyDAO();
		boolean flag = replyDAO.insert(replyDTO);
		
		if(flag) {
			System.out.println("WriteReplyAction 로그 : 댓글 작성 성공");
			forward.setPath("boardDetail.do?board_num="+board_num);
			forward.setRedirect(true);
		}
		else {
			System.out.println("WriteReplyAction 로그 : 댓글 작성 실패");
			forward.setPath("boardDetail.do?board_num="+board_num);
			forward.setRedirect(true);
		}
		return forward;
	}

}
