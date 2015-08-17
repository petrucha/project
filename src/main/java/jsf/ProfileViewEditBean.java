package jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.User;

public class ProfileViewEditBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

private User currentUser;

	public User getCurrentUser() {
	  	FacesContext context = FacesContext.getCurrentInstance();
    	LoginBean log = context.getApplication().evaluateExpressionGet(context, "#{loginBeen}", LoginBean.class);
    	currentUser = log.getUserBean().getUser();

    	if(currentUser == null)
    	System.out.println("currentUser is NULL!!!");
    	else 
    		System.out.println(currentUser.toString());
    	return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}