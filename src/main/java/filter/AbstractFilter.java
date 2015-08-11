package filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import entity.User;
import util.Role;

public class AbstractFilter {

	public AbstractFilter() {
		super();
	}
	

    protected void goHome(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
	RequestDispatcher rd = req.getRequestDispatcher("/pages/index.xhtml");
	rd.forward(request, response);	
}
	

	protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/pages/login.xhtml");
		rd.forward(request, response);
	}
	
	protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/pages/accessDenied.xhtml");
		rd.forward(request, response);
	}
	
	public boolean isAdminRole(User user) {
		if(user == null)
			return false;
		return (user.getGroup().getGroupname()).equals(Role.ADMINISTRATOR);
	}
	
	public boolean isUserRole(User user) {
		if(user == null)
			return false;
		return (user.getGroup().getGroupname()).equals(Role.USER);
	}
}