package controller.async;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.member.MemberDAO;
import model.member.MemberDTO;

@WebServlet("/checkNickName")
public class CheckNickName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckNickName() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckNickName	GET 요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckNickName	POST 요청 도착");
		
		String nickname=request.getParameter("nickname"); // v에서 넘어온 닉네임값
		
		// 데이터 로그
		System.out.println("nickname : "+nickname);
		
		MemberDTO memberDTO=new MemberDTO();
		memberDTO.setMember_nickname(nickname);
		memberDTO.setMember_condition("CHECK_MEMBER_NICKNAME"); // selectOne 구분을 위한 condition
		
		MemberDAO memberDAO=new MemberDAO();
		// 닉네임으로 selectOne
		memberDTO = memberDAO.selectOne(memberDTO);
		
		PrintWriter out=response.getWriter();
		if(memberDTO!=null && memberDTO.getMember_nickname() != null) {
			out.print(true); // 이미 존재한다면 true
		}
		else {
			out.print(false); // 사용가능한 닉네임이라면 false
		}
	}

}
