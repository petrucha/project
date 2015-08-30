package jsf;

import entity.Device;
import service.DeviceService;
import java.io.Serializable;
import java.util.List;

public class AlarmDeviceBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Device> devices;
	private List<Device> selectedDevicesTabLeft;
	private List<Device> selectedDevicesTabRight;
	
	private boolean temperature =true;

	private Device selectedDevice;
	private static DeviceService deviceService = DeviceService.getInstance();

	public List<Device> getDevices() {
		devices = deviceService.getAllDevices(false);
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}

	public List<Device> getSelectedDevicesTabLeft() {
		return selectedDevicesTabLeft;
	}

	public void setSelectedDevicesTabLeft(List<Device> selectedDevicesTabLeft) {
		this.selectedDevicesTabLeft = selectedDevicesTabLeft;
	}

	public List<Device> getSelectedDevicesTabRight() {
		return selectedDevicesTabRight;
	}

	public void setSelectedDevicesTabRight(List<Device> selectedDevicesTabRight) {
		this.selectedDevicesTabRight = selectedDevicesTabRight;
	}

	public void move() {
		if (selectedDevicesTabLeft != null)
			this.setSelectedDevicesTabRight(selectedDevicesTabLeft);
	}

	public void refreshLeftTab() {
		if (selectedDevicesTabLeft != null)
			this.selectedDevicesTabLeft.clear();

	}

	public void clearRightTab() {
		if (selectedDevicesTabRight != null)
			this.selectedDevicesTabRight.clear();
	}

	public void deleteSelected() {
		if (this.selectedDevice != null) {
			this.getSelectedDevicesTabRight().remove(this.getSelectedDevice());
			this.setSelectedDevice(null);
		}
	}
	
	public boolean isTemperature(){
		System.out.println("isTemperature = " + temperature);
		return temperature;
	}
	
	public void setTemperature(boolean temperature){
		System.out.println("setTemperature = " + temperature);
		this.temperature = temperature;
	}
}
