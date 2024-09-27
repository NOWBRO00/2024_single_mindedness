package controller.board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.board.BoardDAO;
import model.board.BoardDTO;
import model.file.FileDAO;
import model.file.FileDTO;
@MultipartConfig
public class WriteBoardAction implements Action {
	private final static String PATH="C:\\JANG\\workspace02\\team1\\src\\main\\webapp\\img\\board\\";
	// 글 제목
	// 작성자
	// 글 내용
	// 파일
	// insert
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("WriteBoardAction 시작");
		
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
		
		String board_title = request.getParameter("board_title");
		String board_content = request.getParameter("board_content");
        
		//데이터 로그
		System.out.println("member_id : "+login_member_id);
		System.out.println("board_title : "+board_title);
		System.out.println("board_content : "+board_content);
		

        // boardDTO 설정
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoard_title(board_title);
        boardDTO.setBoard_content(board_content);
        boardDTO.setBoard_writer_id(login_member_id);
        boardDTO.setBoard_condition("BOARD_INSERT");

        BoardDAO boardDAO = new BoardDAO();
        boolean flag = boardDAO.insert(boardDTO);
        int board_num = 0;
        if (flag) { // 게시글 작성이 성공했다면
            boardDTO.setBoard_condition("LAST_BOARD_NUM");
            boardDTO = boardDAO.selectOne(boardDTO);
            if (boardDTO == null) {
                System.out.println("WriteBoardAction 로그 : 글 selectOne 실패");
                
                request.setAttribute("msg", "글 작성에 실패했습니다.");
    			request.setAttribute("path", "boardWritePage.do");
    			
    			forward.setPath("info.jsp");
    			forward.setRedirect(false);
    			return forward;
            }
            board_num = boardDTO.getBoard_num();

            // 파일 업로드
            Collection<Part> files = null;
            try {
            	System.out.println(request.getContentType());
                files = request.getParts();
            } catch (Exception e) {
                System.out.println("파일 업로드 실패");
                System.out.println(e.getMessage());
            }

            FileDAO fileDAO = new FileDAO();
            for (Part file : files) {
                if (file.getSubmittedFileName() != null && !file.getSubmittedFileName().isEmpty() && file.getSize() > 0) {
                    System.out.println("WriteBoardAction 로그 : 이미지 업로드 시작");

                    String originalFileName = Paths.get(file.getSubmittedFileName()).getFileName().toString(); // 원본 이름
                    String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자 추출
                    UUID uuid = UUID.randomUUID();
                    String imagepath = uuid.toString() + extension; // UUID와 확장자 결합

                    // 이미지 업로드 로직
                    File uploadFile = new File(PATH, imagepath);
                    try (InputStream input = file.getInputStream();
                         FileOutputStream output = new FileOutputStream(uploadFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = input.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                    } catch (Exception e) {
                        System.out.println("파일 저장 중 오류 발생");
                        e.printStackTrace();
                        flag = false; // 파일 저장 실패 시 플래그 설정
                    }

                    // 파일 DTO 설정
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setFile_dir(PATH); // 경로
                    fileDTO.setFile_extension(extension); // 확장자
                    fileDTO.setFile_original_name(originalFileName);
                    fileDTO.setFile_name(imagepath);
                    fileDTO.setFile_board_num(board_num); // 게시판 번호
                    fileDTO.setFile_condition("BOARD_FILE_INSERT"); // 글
                    System.out.println("WriteBoardAction 로그 : 이미지 업로드 끝");

                    // 파일 DAO에 insert
                    boolean fileFlag = fileDAO.insert(fileDTO);
                    if (!fileFlag) {
                        System.out.println("파일 업로드 실패: " + fileDTO.getFile_name());
                        flag = false;
                    } else {
                        System.out.println(fileDTO.getFile_name() + " 업로드 성공");
                    }
                }
            }
        }

        if (flag) {
        	System.out.println("boardDetailAction 로그 : 글 작성 성공");
            forward.setPath("boardDetail.do?board_num="+board_num);
            forward.setRedirect(true);
        } else {
        	System.out.println("boardDetailAction 로그 : 글 작성 실패");
        	 request.setAttribute("msg", "글 작성에 실패했습니다. 다시 시도해주세요.");
 			request.setAttribute("path", "writeBoardPage.do");
 			
 			forward.setPath("info.jsp");
 			forward.setRedirect(false);
        }
        System.out.println("WriteBoardAction 끝");
        return forward;
	}
}