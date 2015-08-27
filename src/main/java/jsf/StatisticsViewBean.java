package jsf;

import java.io.Serializable;

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
	private int recordsPerMinute;
	
	public StatisticsViewBean() {
		this.usersRegistered = userService.getNumberOfUsers();
		this.devicesAdded = deviceService.getNumberOfDevices(null);
		this.recordsPerMinute = recordService.getNumberOfLastRecords(null, DateUtil.getThreeMonthsAgo());
	}

	public int getUsersRegistered() {
		return usersRegistered;
	}

	public int getDevicesAdded() {
		return devicesAdded;
	}

	public int getRecordsPerMinute() {
		return recordsPerMinute;
	}

}