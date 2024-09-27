package controller.async.mail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet("/sendMail")
public class sendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public sendMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // 클라이언트로부터 전송된 요청의 인코딩을 설정합니다.
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            // 인코딩 설정 중 오류 발생 시 예외를 출력합니다.
            e.printStackTrace();
        }
        
        // 요청 파라미터에서 인증번호와 수신자 이메일을 가져옵니다.
        String num = request.getParameter("num");
        String receiver = request.getParameter("email");
        System.out.println("receiver : "+receiver);
        //String subject = request.getParameter("subject");
        // 이메일 제목을 설정합니다.
        String subject ="고래밥 회원가입 인증번호입니다.";
        // 이메일 내용에 인증번호를 포함합니다.
        String content = "인증번호" + num;
        
        // 응답의 콘텐츠 타입을 설정하여 HTML 형식으로 설정합니다.
        response.setContentType("text/html; charset=utf-8");
        
        try {
            // 이메일 서버의 속성을 설정합니다.
            Properties p = System.getProperties(); // 서버 정보를 저장할 Properties 객체
            
            // SMTP 서버의 속성을 설정합니다.
            p.put("mail.smtp.starttls.enable", "true"); // STARTTLS를 사용하여 암호화된 연결을 설정합니다.
            p.put("mail.smtp.host", "smtp.gmail.com"); // SMTP 서버 호스트 (Gmail)
            p.put("mail.smtp.auth", "true"); // 인증을 사용합니다.
            p.put("mail.smtp.port", "587"); // SMTP 포트 번호 (587은 TLS 포트)
            
            // 인증 정보를 생성합니다.
            Authenticator auth = new GoogleAuthentication(); // GoogleAuthentication 클래스에서 인증 정보 생성
            
            // 메일 세션을 생성합니다.
            Session s = Session.getInstance(p, auth); // 서버 속성과 인증 정보를 사용하여 Session 객체 생성
            
            // 메일 메시지를 생성합니다.
            Message m = new MimeMessage(s); // 생성된 Session을 사용하여 MimeMessage 객체 생성
            
            // 수신자의 이메일 주소를 설정합니다.
            Address receiver_address = new InternetAddress(receiver); // 수신자 이메일 주소
            
            // 메일 전송에 필요한 설정을 진행합니다.
            m.setHeader("content-type", "text/html;charset=utf-8"); // 메일의 내용 유형과 문자 인코딩 설정
            m.addRecipient(Message.RecipientType.TO, receiver_address); // 수신자 추가
            m.setSubject(subject); // 메일 제목 설정
            m.setContent(content, "text/html;charset=utf-8"); // 메일 내용과 형식 설정
            m.setSentDate(new Date()); // 메일 전송 날짜 설정
            
            // 메일을 전송합니다.
            Transport.send(m); // 메일을 전송합니다.
            
            // 메일 전송 성공 시에 대한 메시지를 응답에 작성할 수 있습니다
            
        } catch (Exception e) {
            // 메일 전송 중 오류가 발생하면 예외를 출력합니다.
            e.printStackTrace();
        }

	}

}
