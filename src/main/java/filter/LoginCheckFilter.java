package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import entity.User;

public class LoginCheckFilter extends AbstractFilter implements Filter {
	private static List<String> allowedURIs;

	public void init(FilterConfig fConfig) throws ServletException {
		if (allowedURIs == null) {
			allowedURIs = new ArrayList<String>();
			allowedURIs.add(fConfig.getInitParameter("loginActionURI"));
			
/*		    allowedURIs.add("/dp/javax.faces.resource/main8.css.xhtml");
		    allowedURIs.add("/dp/javax.faces.resource/style.css.xhtml");
		    allowedURIs.add("/dp/javax.faces.resource/theme.css.xhtml");
			allowedURIs.add("/dp/pages/index.xhtml");
			allowedURIs.add("/dp/pages/login.xhtml");
			allowedURIs.add("/dp/pages/firstReg/firstRegistration.xhtml");
			allowedURIs.add("/dp/pages/firstReg/firstRegConfirmation.xhtml");
		    allowedURIs.add("/dp/javax.faces.resource/images/background.jpg.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/images/header.jpg.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/images/footer.jpg.xhtml");
			
			allowedURIs.add("/dp/javax.faces.resource/primefaces.css.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/primefaces.js.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/jquery/jquery-plugins.js.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/jquery/jquery.js.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/images/ui-icons_616161_256x240.png.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/messages/messages.png.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/images/ui-bg_highlight-hard_80_85b2cb_1x100.png.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/images/ui-bg_inset-hard_65_85b2cb_1x100.png.xhtml");
			allowedURIs.add("/dp/javax.faces.resource/images/ui-bg_highlight-hard_80_c4c4c4_1x100.png.xhtml");
					*/
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			goHome(request, response, req);
			return;
		}

		User user = (User) session.getAttribute("currentUser");
		if (user == null && !allowedURIs.contains(req.getRequestURI())) {
			System.out.println(req.getRequestURI());
			doLogin(request, response, req);
			return;
		}

		chain.doFilter(request, response);

	}

}