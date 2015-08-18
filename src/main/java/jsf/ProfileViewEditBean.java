package jsf;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import entity.User;
import service.UserService;
import util.PasswordHash;

public class ProfileViewEditBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private User currentUser;
	private String oldPassword = "";
	private String newPassword = "";

	public User getCurrentUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userB = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);
		currentUser = userB.getUser();
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void saveProfile() {
		UserService userService = UserService.getInstance();
		userService.addUser(currentUser);
	}

	public void savePassword() {
		if (currentUser.getPassword().equals(PasswordHash.hash(oldPassword))) {
			currentUser.setPassword(PasswordHash.hash(newPassword));
			UserService userService = UserService.getInstance();
			userService.addUser(currentUser);
		}
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}