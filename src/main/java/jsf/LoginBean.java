package jsf;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.User;
import service.UserService;
import util.PasswordHash;
import util.Role;

public class LoginBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UserBean userBeen;
	private String username;
	private String password;

	public LoginBean() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle rb = ResourceBundle.getBundle("i18n.messages", context.getViewRoot().getLocale());

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		UserService userService = UserService.getInstance();

		User user = userService.getUserByUsernameAndPassword(username,
				PasswordHash.hash(password));
		if (user != null) {
			userBeen.setUser(user);
			String userGroup = user.getGroup().getGroupname();
			request.getSession().setAttribute("currentUser", user);
			if (userGroup != null) {
				if (userGroup.equals(Role.ADMINISTRATOR)) {
					return "admin";
				} else if (userGroup.equals(Role.USER)) {
					return "user";
				}
			}
		}

		else {
			if (userService.isFirstUser())
				return "first";
		}
		this.displayErrorMessageToUser(rb.getString("check.your.username.password"));
		return "failure";
	}

	public void setUserBean(UserBean userBean) {
		this.userBeen = userBean;
	}

	public UserBean getUserBean() {
		return this.userBeen;
	}

}