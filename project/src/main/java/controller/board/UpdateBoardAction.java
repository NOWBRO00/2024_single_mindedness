package controller.board;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.BoardDAO;
import model.board.BoardDTO;

public class UpdateBoardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("UpdateBoardAction 시작");
		
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
		String board_title = request.getParameter("board_title");
		String board_content = request.getParameter("board_content");
		
		//데이터 로그
		System.out.println("board_title : "+board_title);
		System.out.println("boarad_content : "+board_content);
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoard_num(board_num);
		boardDTO.setBoard_title(board_title);
		boardDTO.setBoard_content(board_content);
		
		BoardDAO boardDAO = new BoardDAO();
		boolean flag = boardDAO.update(boardDTO);
		
		if(flag) {
			System.out.println("UpdateBoardAction 로그 : 성공");
			forward.setPath("boardDetail.do?board_num="+board_num);
		}
		else {
			System.out.println("UpdateBoardAction 로그 : 실패");
			request.setAttribute("msg", "게시글 수정에 실패했습니다. 다시 시도해주세요.");
			request.setAttribute("path", "boardDetail.do?board_num="+board_num);
			
			forward.setPath("info.jsp");
			forward.setRedirect(false);
		}
		System.out.println("UpdateBoardAction 끝");
		return forward;
	}

}
