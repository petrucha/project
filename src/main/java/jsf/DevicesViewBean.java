package jsf;

import java.io.Serializable;
import java.util.List;

import data.DeviceData;
import service.DeviceService;

public class DevicesViewBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static DeviceService deviceService = DeviceService.getInstance();
	
	private List<DeviceData> devices;
	

	public DevicesViewBean() {
		this.devices = deviceService.getDevicesData();
	}

	public List<DeviceData> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceData> devices) {
		this.devices = devices;
	}
	
}