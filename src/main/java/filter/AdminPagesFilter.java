package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import entity.User;

public class AdminPagesFilter extends AbstractFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession(true).getAttribute("currentUser");

/*		if (!((user.getGroup().getGroupname()).equals(Role.ADMINISTRATOR))) {
			accessDenied(request, response, req);
			return;
		}*/
		if (!isAdminRole(user)) {
			accessDenied(request, response, req);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}