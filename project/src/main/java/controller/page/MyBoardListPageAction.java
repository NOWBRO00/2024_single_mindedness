package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.board.BoardDAO;
import model.board.BoardDTO;

public class MyBoardListPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("MyBoardListPageAction 시작");

		String login_member_id = LoginCheck.loginCheck(request);

		//데이터 로그
		System.out.println("member_id : "+login_member_id);

		ActionForward forward=new ActionForward();
		if(login_member_id.equals("")) {
			System.out.println("로그인 세션 없음");
			request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
			request.setAttribute("path", "loginPage.do");

			forward.setPath("info.jsp");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("MyBoardListPageAction 로그 : 내가 쓴글 추출 시작");
		BoardDTO boardDTO=new BoardDTO();
		boardDTO.setBoard_writer_id(login_member_id);
		boardDTO.setBoard_condition("MYBOARD_LIST");

		BoardDAO boardDAO=new BoardDAO();
		ArrayList<BoardDTO> myBoardList = boardDAO.selectAll(boardDTO);

		request.setAttribute("myBoardList", myBoardList);

		System.out.println("MyBoardListPageAction 로그 : 내가 쓴글 추출 끝");

		forward.setPath("myBoardList.jsp");
		forward.setRedirect(false);
		
		System.out.println("MyBoardListPageAction 끝");
		return forward;
	}

}
