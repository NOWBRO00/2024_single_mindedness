package controller.board;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.board.BoardDAO;
import model.board.BoardDTO;
import model.file.FileDAO;
import model.file.FileDTO;
import model.like.LikeDAO;
import model.like.LikeDTO;
import model.reply.ReplyDAO;
import model.reply.ReplyDTO;

public class BoardDetailAction implements Action  {
	// 게시글
	// 댓글
	// 파일
	// 좋아요 수
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardDetailsAction 시작");
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String member_id=(String)request.getSession().getAttribute("member_id");
		//데이터 로그
		System.out.println("board_num : "+board_num);
		System.out.println("member_id : "+member_id);
		
		BoardDTO boardDTO=new BoardDTO();
		boardDTO.setBoard_num(board_num); // 글번호
		boardDTO.setBoard_condition("BOARD_SELECT_ONE");
		
		BoardDAO boardDAO=new BoardDAO();
		boardDTO = boardDAO.selectOne(boardDTO); // 글번호로 게시글 찾기
		
		//데이터 로그
		System.out.println("board_num : "+boardDTO.getBoard_num());
		System.out.println("board_title : "+boardDTO.getBoard_title());
		System.out.println("board_regist_date : "+boardDTO.getBoard_registration_date());
		
		FileDTO fileDTO=new FileDTO();
		fileDTO.setFile_board_num(board_num);
		fileDTO.setFile_condition("BOARD_FILE_SELECTALL");
		FileDAO fileDAO=new FileDAO();
		ArrayList<FileDTO> fileList = new ArrayList<FileDTO>();
		fileList = fileDAO.selectAll(fileDTO); // 글 번호로 해당 글 파일 찾기
		
		ReplyDTO replyDTO=new ReplyDTO();
		replyDTO.setReply_board_num(board_num);
		ReplyDAO replyDAO=new ReplyDAO();
		ArrayList<ReplyDTO> replyList=new ArrayList<ReplyDTO>();
		replyList = replyDAO.selectAll(replyDTO);
		System.out.println(replyList);
		
		
		if (member_id != null) {
            System.out.println("boardDetailAction 로그 : 사용자 세션이 있습니다");
            LikeDTO likeDTO=new LikeDTO();
            likeDTO.setLike_board_num(board_num);
            likeDTO.setLike_member_id(member_id);
            
            LikeDAO likeDAO=new LikeDAO();
            likeDTO = likeDAO.selectOne(likeDTO);
            
            request.setAttribute("like_member", likeDTO != null ? "true" : "false");
            /*if(likeDTO !=null) { // 사용자가 좋아요를 눌렀다면
            	request.setAttribute("like_member", "true");
            }
            else { // 사용자가 좋아요를 안눌렀다면
            	request.setAttribute("like_member", "false");
            }*/
        }
		
		
		request.setAttribute("board", boardDTO);
		request.setAttribute("fileList", fileList);
		request.setAttribute("replyList", replyList);
		
		ActionForward forward=new ActionForward();
		forward.setPath("boardDetails.jsp");
		forward.setRedirect(false);
		System.out.println("BoardDetailsAction 끝");
		return forward;
	}

}