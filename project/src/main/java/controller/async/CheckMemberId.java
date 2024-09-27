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

@WebServlet("/checkMemberId")
public class CheckMemberId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckMemberId() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckMemberId	GET요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckMemberId	POST요청 도착");
		
		// 아이디 중복검사
		// email / id(PK)
		String member_id=request.getParameter("email");
		// 데이터 로그
		System.out.println("member_id : "+member_id);
		
		MemberDTO memberDTO=new MemberDTO();
		memberDTO.setMember_id(member_id); // v에서 넘어온 email
		memberDTO.setMember_condition("CHECK_MEMBER_ID"); // selectOne 구분을 위한 컨디션
		
		MemberDAO memberDAO=new MemberDAO();
		// v에서 넘어온 email로 selectOne
		memberDTO = memberDAO.selectOne(memberDTO);
		
		PrintWriter out=response.getWriter();
		if(memberDTO!=null && memberDTO.getMember_id() != null) {
			out.print(true); // 이미 존재한다면 true
		}
		else {
			out.print(false); // 사용가능한 아이디라면 false
		}
	}

}
