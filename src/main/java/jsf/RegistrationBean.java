package jsf;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import service.GroupService;
import service.UserService;
import util.PasswordHash;
import entity.Group;
import entity.User;

public class RegistrationBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static GroupService groupService = GroupService.getInstance();
	private static UserService userService = UserService.getInstance();
	private String username;
	private String password;
	private String confirmpassword;
	private String firstname;
	private String lastname;
	private Date birthday;
	private String email;

	private String groupname;

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
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

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String registerUser() {

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle rb = ResourceBundle.getBundle("i18n.messages", context.getViewRoot().getLocale());

		User user = new User();
		user.setUsername(username);
		user.setPassword(PasswordHash.hash(password));
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setBirthday(birthday);
		user.setEmail(email);

		Group group = groupService.getGroupByName(groupname);
		if (group == null) {
			group = new Group();
			group.setGroupname(groupname);
			groupService.addGroup(group);
		}

		user.setGroup(group);
		if (userService.addUser(user)) {
			FacesMessage msg = new FacesMessage(rb.getString("successful"), MessageFormat.format(rb.getString("welcome.0"), user.getFirstname()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "success";
		} else {
			FacesMessage msg = new FacesMessage(rb.getString("failure"), rb.getString("please.try.again"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "failure";
		}
	}

}
