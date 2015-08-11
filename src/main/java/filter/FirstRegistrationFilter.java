package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import service.UserService;

public class FirstRegistrationFilter extends AbstractFilter implements Filter {

	private int countView;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		countView = 0;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		UserService userService = UserService.getInstance();

		String pathInfo = req.getRequestURL().toString();
		String pageName = pathInfo.substring(pathInfo.length() - 26, pathInfo.length());

		if (!userService.isFirstUser() && !pageName.equals("firstRegConfirmation.xhtml")) {
			accessDenied(request, response, req);
			return;
		}
		
		if (pageName.equals("firstRegConfirmation.xhtml") && countView != 0) {
			accessDenied(request, response, req);
			return;
		}

		if (pageName.equals("firstRegConfirmation.xhtml") && countView == 0) {
			countView++;
		}
		chain.doFilter(request, response);
	}

	public void destroy() {

	}
}