package util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import service.DeviceService;

public class MacValidator implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		String mac = value.toString();
		DeviceService deviceService = DeviceService.getInstance();
		
		if(deviceService.isDeviceExist(mac)){
			FacesMessage msg = 
					new FacesMessage("MAC address validation failed.", 
							"MAC address already exist.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
	}
	
}