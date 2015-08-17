package jsf;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import util.Role;
import entity.User;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	public boolean isAdminRole() {
		if(user == null)
			return false;
		return (user.getGroup().getGroupname()).equals(Role.ADMINISTRATOR);
	}

	public boolean isUserRole() {
		if(user == null)
			return false;
		return (user.getGroup().getGroupname()).equals(Role.USER);
	}

	public String logout() {
		getRequest().getSession().invalidate();
		return "/pages/guest/home.xhtml?faces-redirect=true";

	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}