package controller.async;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.BoardDAO;
import model.board.BoardDTO;
import model.like.LikeDAO;
import model.like.LikeDTO;

@WebServlet("/boardLike")
public class BoardLike extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
     public BoardLike() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET 요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청 도착");
		// 데이터(게시판 번호와 로그인된 사용자의 id)를 받아와
		// 데이터로 like DB에 selectOne
		// null 아니면 이미 해당 게시물을 좋아요한 사용자(게시물, 아이디 당 1개의 좋아요)
		
		// likeDTO가 null 이면 insert
		// null이 아니면 delete
		
		// 이후 받아온 board_num으로 board selectOne == 좋아요한 개수을 받아옴
		
		// json으로 insert면 true
		// delete면 false
		
		// 해당 게시물의 좋아요 개수
		
		// 를 반환함
		
		int board_num = Integer.parseInt(request.getParameter("board_num")); // 게시물 번호 PK
		String member_id = (String)request.getSession().getAttribute("member_id"); // 로그인한 사용자 id PK
		
		// 데이터 로그
		System.out.println("board_num : "+board_num);
		System.out.println("member_id : "+member_id);
		
		LikeDTO likeDTO = new LikeDTO();
		likeDTO.setLike_member_id(member_id);
		likeDTO.setLike_board_num(board_num);
		
		LikeDAO likeDAO=new LikeDAO();
		likeDTO = likeDAO.selectOne(likeDTO);
		
		response.setContentType("application/json"); // JSON으로 응답 설정

		boolean flag;
		String jsonResponse;
		PrintWriter out=response.getWriter();

		LikeDTO newLikeDTO = new LikeDTO();
		newLikeDTO.setLike_member_id(member_id);
		newLikeDTO.setLike_board_num(board_num);
		if(likeDTO == null) {
			flag = likeDAO.insert(newLikeDTO);
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setBoard_num(board_num);
			boardDTO.setBoard_condition("BOARD_SELECT_ONE");
			
			BoardDAO boardDAO = new BoardDAO();
			boardDTO = boardDAO.selectOne(boardDTO);
			// json형식으로 데이터 set
			jsonResponse = "{\"flag\": " + "true" + "," + "\"likeCnt\": " + boardDTO.getBoard_like_cnt() + "}";
		}
		else {
			flag = likeDAO.delete(likeDTO);
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setBoard_num(board_num);
			boardDTO.setBoard_condition("BOARD_SELECT_ONE");
			
			BoardDAO boardDAO = new BoardDAO();
			boardDTO = boardDAO.selectOne(boardDTO);
			// json형식으로 데이터 set
			jsonResponse = "{\"flag\": " + "false" + "," + "\"likeCnt\": " + boardDTO.getBoard_like_cnt() + "}";
		}
		// json 응답
		out.print(jsonResponse);
	    out.flush();
	}
	// 좋아요를 셀렉트
	// 있으면 꺼야되고
	// 없으면 켜야됨
}
