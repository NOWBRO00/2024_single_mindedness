package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.BoardDAO;
import model.board.BoardDTO;

public class UpdateBoardPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("UpdateBoardPageAction 시작");
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
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoard_num(board_num);
		
		BoardDAO boardDAO = new BoardDAO();
		boardDTO = boardDAO.selectOne(boardDTO);
		
		request.setAttribute("board", boardDTO);
		
		forward.setPath("updateBoard.jsp");
		forward.setRedirect(false);
		System.out.println("UpdateBoardPageAction 끝");
		return forward;
	}

}
