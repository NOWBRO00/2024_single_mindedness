package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.BoardDAO;
import model.board.BoardDTO;

public class BoardListPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardListPageAction 시작");

		//		셀렉트올 인데 ? 검색 키워드 받아야하고
		//				셀렉트 할 때 파일도 가져와야 됨 


		String searchContent = request.getParameter("searchContent");
		String searchKeyword = request.getParameter("searchKeyword");

		// 데이터 로그
		System.out.println("searchContent : " + searchContent);

		BoardDTO boardDTO = new BoardDTO();
		BoardDAO boardDAO = new BoardDAO();
		
		if (searchContent == null || searchContent.isEmpty()) {
			boardDTO.setBoard_condition("BOARD_ALL");
			boardDTO.setBoard_page_num(1);
			System.out.println("로그 33 board_page_num:"+boardDTO.getBoard_page_num());
		} 
		else if (searchContent.equals("BOARD_CONTENT")) {
			boardDTO.setBoard_searchKeyword(searchKeyword); // 검색 키워드 설정
			boardDTO.setBoard_condition("BOARD_CONTENT");
		} 
		else if (searchContent.equals("BOARD_LIKE")) {
			boardDTO.setBoard_condition("BOARD_LIKE");
		} 
		else {
			System.out.println("잘못된 접근");
			// 기본 조건 설정 혹은 예외 처리
			boardDTO.setBoard_condition("BOARD_ALL");
			System.out.println("로그 33 board_page_num:"+boardDTO.getBoard_page_num());
		}
		// 페이지 번호를 V에서 받아와 M에게 데이터 요청
		String board_page = request.getParameter("currentPage");
		System.out.println("boardPageAction board_page : " + board_page);


		// 만약 받아온 페이지 번호가 null 이라면 기본값 1
		int board_page_num;

		if(board_page != null) {	
			System.out.println("boardPageAction 받아온 페이지 번호 null이 아닌 경우");
			// 받아온 페이지 번호가 null이 아니라면
			// int 타입으로 변환
			board_page_num = Integer.parseInt(board_page);
			// M에게 전달하기 위해 DTO에 담아준다.
			boardDTO.setBoard_page_num(board_page_num);
		}
		else {
			System.out.println("boardPageAction 받아온 페이지 번호 null인 경우");
			// M에게 전달하기 위해 DTO에 담아준다.
			board_page_num =1;
			boardDTO.setBoard_page_num(board_page_num);
		}

		// 게시판 페이지 개수
		BoardDTO page_count = new BoardDTO();
		page_count.setBoard_page_num(board_page_num);
		page_count.setBoard_condition("BOARD_PAGE_COUNT");
		page_count = boardDAO.selectOne(page_count);
		// int 타입 변수에 받아온 값을 넣어준다.
		int board_page_count = page_count.getBoard_total_page();

		if (board_page_count < 1) {
			board_page_count = 1; // 최소 페이지 수를 1로 설정
		}
		
		//M에게 데이터를 보내주고, 결과를 ArrayList로 반환받는다.
		ArrayList<BoardDTO> boardList = boardDAO.selectAll(boardDTO);

		System.out.println(boardList);


		request.setAttribute("boardList", boardList);
		request.setAttribute("board_page_count", board_page_count); // 게시판 페이지 개수
		request.setAttribute("currentPage", board_page_num); // 게시판 현재 페이지

		ActionForward forward = new ActionForward();
		forward.setPath("boardList.jsp"); // 경로 확인
		forward.setRedirect(false);
		System.out.println("BoardListPageAction 끝");
		return forward;
	}



}
