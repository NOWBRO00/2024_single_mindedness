package controller.async;

import java.io.IOException;
import java.io.PrintWriter;

import controller.payment.PaymentUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.payment.PaymentInfo;

@WebServlet("/payment/prepared")
public class PaymentPrepared extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PaymentPrepared() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PaymentPrepared : GET 요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PaymentPrepared : POST 요청 도착");
		String merchant_uid = request.getParameter("merchant_uid");
		System.out.println(merchant_uid);
		
		// 토큰 발행
		PaymentInfo paymentInfo = PaymentUtil.portOne_code();
		System.out.println("portone token : "+paymentInfo.getToken());
		
		paymentInfo.setMerchant_uid(merchant_uid);
		
		paymentInfo = PaymentUtil.prepareReult(paymentInfo);
		
		response.setContentType("application/json"); // JSON으로 응답 설정
	    PrintWriter out = response.getWriter();
	    
	    //System.out.println(paymentInfo.getAmount());
	    String jsonResponse;
	    
	    if (paymentInfo.getAmount() > 0) {
	        jsonResponse = "{\"amountRes\": " + paymentInfo.getAmount() + "}";
	        System.err.println("JSON 응답: " + jsonResponse); // JSON 응답을 로그에 출력
	    } else {
	        jsonResponse = "{\"amountRes\": 0}"; // JSON 형식으로 0 반환
	        System.err.println("JSON 응답: " + jsonResponse); // JSON 응답을 로그에 출력
	    }
	    
	    out.print(jsonResponse);
	    out.flush();
	}
}
