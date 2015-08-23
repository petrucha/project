package jsf;

import java.io.Serializable;
import java.util.List;

import data.DeviceData;
import entity.Device;
import service.DeviceService;

public class DevicesViewBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static DeviceService deviceService = DeviceService.getInstance();
	
	private List<DeviceData> devices;
	
	private DeviceData selectedDevice;

	public DevicesViewBean() {
		this.devices = deviceService.getDevicesData();
	}

	public List<DeviceData> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceData> devices) {
		this.devices = devices;
	}

	public DeviceData getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(DeviceData selectedDevice) {
		this.selectedDevice = selectedDevice;
		System.out.println(selectedDevice.getDeviceId());
	}
	
	public void deleteDevice() {
		Device dev = deviceService.getDevice(selectedDevice.getDeviceId());
		if (dev!=null) {
			deviceService.deleteDevice(dev);
		}
		selectedDevice = null;
	}
	
}