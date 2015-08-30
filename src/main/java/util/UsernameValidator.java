package util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import service.UserService;

public class UsernameValidator implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		String username = value.toString();
		UserService userService = UserService.getInstance();
		
		if(userService.isUserExist(username)){
			FacesMessage msg = 
					new FacesMessage("Username validation failed.", 
							"Username already exist.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
	}
	
}