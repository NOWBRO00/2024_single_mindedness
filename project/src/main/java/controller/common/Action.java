package controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Action {
	ActionForward execute(HttpServletRequest request, HttpServletResponse response);
}