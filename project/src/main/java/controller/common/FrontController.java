package controller.common;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.do")
@MultipartConfig
// 톰캣(server)이 구동될때 xxx.do로 끝나는 요청에 대해여 FC를 호출하게됨
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 맵버 변수로 핸들러맵퍼를 갖음(재사용을 위함)
	private HandelerMapper mappings;
       
    public FrontController() {
        super();
        // FrontController가 생성될 때 핸들러맵퍼도 같이 생성
        // new연산을 줄이기 위함(싱글톤 패턴)
        this.mappings=new HandelerMapper();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doAction으로 가
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doAction으로 가
		doAction(request, response);
	}
	
	private void doAction(HttpServletRequest request, HttpServletResponse response) {
		// 1. 사용자 요청 추출 및 확인
		String uri=request.getRequestURI(); // uri 추출 ~/main.do 등
		String cp=request.getContextPath(); // contextPath 추출 현 프로젝트는 team1
		String command=uri.substring(cp.length()); 
		// subString은 문자열을 잘라줌 == uri에 team1을 자르고 요청만 추출
		System.out.println("command :"+command); // 요청 확인용
		
		// 2. 요청을 수행
        // 요청을 인자로 넘기면 맵핑되어있는 액션이 돌아옴
		Action action = this.mappings.getAction(command);
        // 해당 액션으로 요청 수행
		ActionForward forward = action.execute(request, response);

		// 3. 응답(페이지 이동 등)
		//  1) 전달할 데이터가 있니? 없니? == 포워드? 리다이렉트? 
		//  2) 어디로 갈까? == 경로
		if(forward == null) {
			// command 요청이 없는 경우
		}
		else {
			if(forward.isRedirect()) { // 응답이 redirect면
				try {
					// forward에 경로로 redirect
					response.sendRedirect(forward.getPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				// 서블릿에서 forward를 수행하려면 RequestDispatcher가 필요함
				// dispatcher를 생성하려면 경로가 필요함
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				try {
					dispatcher.forward(request, response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
