package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.DualListModel;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import entity.Device;
import service.DeviceService;
import service.UserService;

public class DeviceEditViewBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static DeviceService deviceService = DeviceService.getInstance();
	
	private static UserService userService = UserService.getInstance();
	
	private Device device;
	
	private int deviceId;
	
	private DualListModel<String> usernames;
	
	public DeviceEditViewBean() {
		
	}


	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
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
	
	public void doUpdate() {
		List<String> selectedUsers = this.usernames.getTarget();
		deviceService.addDeviceToUsers(this.device, selectedUsers);
	}
	
	public void prerender() {
		this.device = deviceService.getDevice(deviceId);
		
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
	
}