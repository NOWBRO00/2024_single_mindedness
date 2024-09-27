package controller.async;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.member.MemberDAO;
import model.member.MemberDTO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkPassword")
public class CheckPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckPassword() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckPassword   GET요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckPassword   POST요청 도착");
		
		// 세션에 저장된 로그인
		String member_id=(String) request.getSession().getAttribute("member_id");
		String member_password=request.getParameter("password");

		System.out.println("member_id : "+member_id);
		System.out.println("member_password : "+member_password );
		
		
		MemberDTO memberDTO=new MemberDTO();
		memberDTO.setMember_id(member_id);
		memberDTO.setMember_condition("MEMBER_PASSWORD_GET");
		
		MemberDAO memberDAO=new MemberDAO();
		memberDTO = memberDAO.selectOne(memberDTO);
		System.out.println("memberDTO : "+memberDTO);
		
		PrintWriter out=response.getWriter();
		System.out.println("memberDTO.getMember_password() : "+memberDTO.getMember_password());
		System.out.println("member_password : "+member_password);
		
		if(memberDTO.getMember_password().equals(member_password)) {
			System.out.println("트루");
			out.print(true); // 이미 존재한다면 true
		}
		else {
			System.out.println("펄스");
			out.print(false); // 사용가능한 닉네임이라면 false
		}
		
		
	}

}
