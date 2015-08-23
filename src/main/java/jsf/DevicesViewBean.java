package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.context.RequestContext;
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
	
	public void openDeviceUsers() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);
        
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> paramList = new ArrayList<String>();
        paramList.add(String.valueOf(selectedDevice.getDeviceId()));
        params.put("deviceId", paramList);
        
        RequestContext.getCurrentInstance().openDialog("deviceUsers", options, params);
    }
	
}