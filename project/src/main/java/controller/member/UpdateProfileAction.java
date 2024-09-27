package controller.member;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import controller.common.Action;
import controller.common.ActionForward;
import controller.util.LoginCheck;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.member.MemberDAO;
import model.member.MemberDTO;
@MultipartConfig
public class UpdateProfileAction implements Action{
//	private final static String DEFAULIMAGEPATH="defaul t.jsp";
	private final static String PATH="D:\\SDS\\workspace02\\project\\src\\main\\webapp\\img\\profile\\";

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 사용자가 업로드한 프로필사진으로 업데이트
		
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
		
		System.out.println("memebr_id : "+login_member_id);
		
		MemberDTO memberDTO=new MemberDTO();
		memberDTO.setMember_id(login_member_id);
		memberDTO.setMember_condition("MEMBER_PROFILE");
		
		MemberDAO memberDAO=new MemberDAO();
		
		Part file=null;
		try {
			file=request.getPart("member_profile");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String imagepath = Paths.get(file.getSubmittedFileName()).getFileName().toString(); // 파일명 받아옴
		if(!imagepath.isEmpty()) { // 파일이 비어있지 않다면
			System.out.println("UpdateProfile 로그 : 이미지 업로드 시작");
			imagepath = login_member_id + (imagepath.substring(imagepath.lastIndexOf("."))); // 파일명을 유저아이디.확장자로 바꿈 ex)admin.jpg
			
			// 이미지 업로드 로직
			File uploadFile=new File(new File(PATH),imagepath);
			try (InputStream input = file.getInputStream();
					FileOutputStream output = new FileOutputStream(uploadFile)) {
				byte[] buffer = new byte[1024];
				int length;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			memberDTO.setMember_profile(imagepath); // 변경된 파일명으로 set
			System.out.println("UpdateProfile 로그 : 이미지 업로드 끝");
		}
		
		boolean flag=memberDAO.update(memberDTO);
		
		// 메인페이지로 이동
		forward.setRedirect(true);
		
		if(flag) {
			forward.setPath("mypage.do");
		}
		else {
			forward.setPath("mypage.do");
		}
		return forward;
	}
}


