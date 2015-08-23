package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import entity.Device;
import service.DeviceService;
import service.UserService;

public class DeviceUsersBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static UserService userService = UserService.getInstance();
	private static DeviceService deviceService = DeviceService.getInstance();
	
	private DualListModel<String> usernames;
	private int deviceId;
	
	public DeviceUsersBean() {
		Map<String, String[]> mapParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
		String[] idParams = mapParams.get("deviceId");
		int deviceId = Integer.parseInt(idParams[0]);
		this.deviceId = deviceId;
		System.out.println(deviceId);
		
		List<String> namesSource = new ArrayList<String>();
        List<String> namesTarget = new ArrayList<String>();
         
        List<String> deviceUsers = userService.getUsernamesByDevice(deviceId);
        for (String user : deviceUsers) {
			namesTarget.add(user);
		}
        List<String> notDeviceUsers = userService.getUsernamesNotHavingDevice(deviceId);
        for (String user : notDeviceUsers) {
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
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public void updateDevice() {
		List<String> selectedUsers = this.usernames.getTarget();
		Device device = deviceService.getDevice(deviceId);
		deviceService.addDeviceToUsers(device, selectedUsers);
	}
}