package jsf;

import java.io.Serializable;
import java.text.MessageFormat;import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import entity.Device;
import service.DeviceService;
import service.UserService;

public class DeviceCreateBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static UserService userService = UserService.getInstance();
	private static DeviceService deviceService = DeviceService.getInstance();
	
	private DualListModel<String> usernames;
	private Device device;
	
	public DeviceCreateBean() {
		device = new Device();
		
		List<String> namesSource = new ArrayList<String>();
        List<String> namesTarget = new ArrayList<String>();
         
        List<String> deviceUsers = userService.getUsernames();
        for (String user : deviceUsers) {
			namesSource.add(user);
		}
         
        this.usernames = new DualListModel<String>(namesSource, namesTarget);
	}

	public DualListModel<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(DualListModel<String> usernames) {
		this.usernames = usernames;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	public String createDevice() {
		List<String> selectedUsers = usernames.getTarget();

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle rb = ResourceBundle.getBundle("i18n.messages", context.getViewRoot().getLocale());
		
		if(deviceService.addDeviceToUsers(device, selectedUsers)){
	        FacesMessage msg = new FacesMessage(rb.getString("successful"), MessageFormat.format(rb.getString("device.created.with.mac.0"), device.getMac()));
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			return "success";}
		else{
	        FacesMessage msg = new FacesMessage(rb.getString("failure"), rb.getString("please.try.again"));
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			return "failure";}
	}
	
}