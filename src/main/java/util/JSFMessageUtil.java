package util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class JSFMessageUtil {

	public void sendInfoMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO,
				"Info", message);
		addMessageToJsfContext(facesMessage);
	}

	public void sendWarnMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_WARN,
				"Warning!", message);
		addMessageToJsfContext(facesMessage);
	}

	public void sendErrorMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_ERROR,
				"Error!", message);
		addMessageToJsfContext(facesMessage);
	}

	public void sendFatalMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_FATAL,
				"Fatal!", message);
		addMessageToJsfContext(facesMessage);
	}

	private FacesMessage createMessage(Severity severity, String messName,
			String mensagemErro) {
		return new FacesMessage(severity, messName, mensagemErro);
	}

	private void addMessageToJsfContext(FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
}