package controller.board;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.BoardDAO;
import model.board.BoardDTO;

public class DeleteBoardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("DeleteBoardAction 시작");

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
		
		//데이터 로그
		System.out.println("board_num : "+board_num);
		
		BoardDTO boardDTO=new BoardDTO();
		boardDTO.setBoard_num(board_num);
		
		BoardDAO boardDAO=new BoardDAO();
		boolean flag=boardDAO.delete(boardDTO);
		
		forward.setRedirect(true);
		if(flag) {
			System.out.println("DeleteBoardAction 로그 : 게시글 삭제 성공");
			forward.setPath("main.do");
		}
		else {
			System.out.println("DeleteBoardAction 로그 : 게시글 삭제 실패");
			request.setAttribute("msg", "게시글 삭제에 실패했습니다. 다시 시도해 주세요.");
			request.setAttribute("path", "boardDetail.do?boardNum="+board_num);
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
		}
		System.out.println("DeleteBoardAction 끝");
		return forward;
	}

}
