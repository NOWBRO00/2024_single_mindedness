package controller.async;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.reply.ReplyDAO;
import model.reply.ReplyDTO;

@WebServlet("/updateReply")
public class UpdateReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public UpdateReply() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET 요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청 도착");
		
		int reply_num=Integer.parseInt(request.getParameter("reply_num"));
		String reply_content=request.getParameter("reply_content");
		
		//데이터 로그
		System.out.println("reply_num : "+reply_num);
		System.out.println("reply_content : "+reply_content);
		
		ReplyDTO replyDTO=new ReplyDTO();
		replyDTO.setReply_num(reply_num);
		replyDTO.setReply_content(reply_content);
		
		ReplyDAO replyDAO=new ReplyDAO();
		boolean flag = replyDAO.update(replyDTO);
		
		PrintWriter out=response.getWriter();
		if(flag) { // 성공
			out.print(flag);
		}
		else { // 실패
			out.print(flag);
		}
	}

}
