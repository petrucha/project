package jsf;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import entity.User;
import service.DeviceService;
import service.RecordService;
import service.UserService;
import util.DateUtil;

public class StatisticsViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static RecordService recordService = RecordService.getInstance();
	private static DeviceService deviceService = DeviceService.getInstance();
	private static UserService userService = UserService.getInstance();
	
	private int usersRegistered;
	private int devicesAdded;
	private int lastRecordsCount;
	private boolean adminMode;
	
	public StatisticsViewBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userB = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);
		if (userB.isAdminRole()) {
			this.adminMode = true;
			this.usersRegistered = userService.getNumberOfUsers();
			this.devicesAdded = deviceService.getNumberOfDevices(null);
			this.lastRecordsCount = recordService.getNumberOfLastRecords(null, DateUtil.getThreeMonthsAgo());
		} else {
			this.adminMode = false;
			User currentUser = userB.getUser();
			String username = currentUser.getUsername();
			this.devicesAdded = deviceService.getNumberOfDevices(username);
			this.lastRecordsCount = recordService.getNumberOfLastRecords(username , DateUtil.getThreeMonthsAgo());
		}

	}

	public int getUsersRegistered() {
		return usersRegistered;
	}

	public int getDevicesAdded() {
		return devicesAdded;
	}

	public int getLastRecordsCount() {
		return lastRecordsCount;
	}

	public boolean isAdminMode() {
		return adminMode;
	}
}