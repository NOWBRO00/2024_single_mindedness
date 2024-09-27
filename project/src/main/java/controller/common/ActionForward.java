package controller.common;

public class ActionForward {
	private boolean redirect; // 어떻게 갈지 == 방식
	private String path;	// 어디로 갈지 == 경로
	public boolean isRedirect() {
		return redirect;
	}
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
