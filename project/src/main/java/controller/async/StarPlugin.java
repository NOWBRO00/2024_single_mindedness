package controller.async;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.review.ReviewDTO;

import java.io.IOException;

public class StarPlugin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StarPlugin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET 요청 도착");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청 도착");
		int star = Integer.parseInt(request.getParameter("star"));
		
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setReview_star(star);
		
		
	}

}
