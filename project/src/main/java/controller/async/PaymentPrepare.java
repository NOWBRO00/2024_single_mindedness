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

@WebServlet("/payment/prepare")
public class PaymentPrepare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PaymentPrepare() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PaymentPrepare : GET 요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PaymentPrepare : POST 요청 도착");
		String merchant_uid = request.getParameter("merchant_uid");
		int amount = Integer.parseInt(request.getParameter("amount"));
		System.out.println(merchant_uid);
		System.out.println(amount);
		
		// 토큰 발행
		PaymentInfo paymentInfo = PaymentUtil.portOne_code();
		System.out.println("portone token : "+paymentInfo.getToken());
		
		paymentInfo.setMerchant_uid(merchant_uid);
		paymentInfo.setAmount(amount);
		
		boolean flag = PaymentUtil.prepare(paymentInfo);
		
		PrintWriter out = response.getWriter();
		if(flag) {
			out.print("true");
		}
		else {
			out.print("false");
		}
	}

}
