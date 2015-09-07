package jsf;

import entity.Device;
import entity.User;
import service.DeviceService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

public class AlarmDeviceBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	// For Select Devices
	private List<Device> devices;
	private List<Device> selectedDevicesTabLeft;
	private List<Device> selectedDevicesTabRight;

	private Device selectedDevice;
	private static DeviceService deviceService = DeviceService.getInstance();
	// For create alrams
	private boolean temperature = false;

	private String temperatureAbType;
	private boolean temperatureAbsolute;
	private double valueTemperatureAbsoluteMin;
	private Date valueTimeTemperatureAbsoluteMin;
	private double valueTemperatureAbsoluteMax;
	private Date valueTimeTemperatureAbsoluteMax;

	private String temperatureAvType;
	private boolean temperatureAverage;
	private double valueTemperatureAverageMin;
	private Date valueTimeTemperatureAverageMin;
	private double valueTemperatureAverageMax;
	private Date valueTimeTemperatureAverageMax;

	private boolean humidity = true;
	private boolean humidityMax;
	private boolean humidityAverage;
	// For options
	private boolean alarmToEmail;
	private String userEmail;

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

	public boolean isTemperature() {
		return temperature;
	}

	public void setTemperature(boolean temperature) {
		this.temperature = temperature;
	}

	public boolean isTemperatureAbsolute() {
		return temperatureAbsolute;
	}

	public void setTemperatureAbsolute(boolean temperatureAbsolute) {
		this.temperatureAbsolute = temperatureAbsolute;
	}

	public boolean isTemperatureAverage() {
		return temperatureAverage;
	}

	public void setTemperatureAverage(boolean temperatureAverage) {
		this.temperatureAverage = temperatureAverage;
	}

	public boolean isHumidity() {
		return humidity;
	}

	public void setHumidity(boolean humidity) {
		this.humidity = humidity;
	}

	public boolean isHumidityMax() {
		return humidityMax;
	}

	public void setHumidityMax(boolean humidityMax) {
		this.humidityMax = humidityMax;
	}

	public boolean isHumidityAverage() {
		return humidityAverage;
	}

	public void setHumidityAverage(boolean humidityAverage) {
		this.humidityAverage = humidityAverage;
	}

	public String getTemperatureAbType() {
		return temperatureAbType;
	}

	public void setTemperatureAbType(String temperatureAbType) {
		this.temperatureAbType = temperatureAbType;
	}

	public String getTemperatureAvType() {
		return temperatureAvType;
	}

	public void setTemperatureAvType(String temperatureAvType) {
		this.temperatureAvType = temperatureAvType;
	}

	public void temperatureOnOffListener() {
		if (!temperature) {
			temperatureAbType = null;
			temperatureAbsolute = false;

			temperatureAvType = null;
			temperatureAverage = false;
		}
	}

	public void temperatureAbsoluteListener() {
		if (!temperatureAbsolute)
			temperatureAbType = null;
	}

	public void temperatureAverageListener() {
		if (!temperatureAverage)
			temperatureAvType = null;
	}

	public double getValueTemperatureAbsoluteMin() {
		return valueTemperatureAbsoluteMin;
	}

	public void setValueTemperatureAbsoluteMin(double valueTemperatureAbsoluteMin) {
		this.valueTemperatureAbsoluteMin = valueTemperatureAbsoluteMin;
	}

	public Date getValueTimeTemperatureAbsoluteMin() {
		return valueTimeTemperatureAbsoluteMin;
	}

	public void setValueTimeTemperatureAbsoluteMin(Date valueTimeTemperatureAbsoluteMin) {
		this.valueTimeTemperatureAbsoluteMin = valueTimeTemperatureAbsoluteMin;
	}

	public double getValueTemperatureAbsoluteMax() {
		return valueTemperatureAbsoluteMax;
	}

	public void setValueTemperatureAbsoluteMax(double valueTemperatureAbsoluteMax) {
		this.valueTemperatureAbsoluteMax = valueTemperatureAbsoluteMax;
	}

	public Date getValueTimeTemperatureAbsoluteMax() {
		return valueTimeTemperatureAbsoluteMax;
	}

	public void setValueTimeTemperatureAbsoluteMax(Date valueTimeTemperatureAbsoluteMax) {
		this.valueTimeTemperatureAbsoluteMax = valueTimeTemperatureAbsoluteMax;
	}

	public double getValueTemperatureAverageMin() {
		return valueTemperatureAverageMin;
	}

	public void setValueTemperatureAverageMin(double valueTemperatureAverageMin) {
		this.valueTemperatureAverageMin = valueTemperatureAverageMin;
	}

	public Date getValueTimeTemperatureAverageMin() {
		return valueTimeTemperatureAverageMin;
	}

	public void setValueTimeTemperatureAverageMin(Date valueTimeTemperatureAverageMin) {
		this.valueTimeTemperatureAverageMin = valueTimeTemperatureAverageMin;
	}

	public double getValueTemperatureAverageMax() {
		return valueTemperatureAverageMax;
	}

	public void setValueTemperatureAverageMax(double valueTemperatureAverageMax) {
		this.valueTemperatureAverageMax = valueTemperatureAverageMax;
	}

	public Date getValueTimeTemperatureAverageMax() {
		return valueTimeTemperatureAverageMax;
	}

	public void setValueTimeTemperatureAverageMax(Date valueTimeTemperatureAverageMax) {
		this.valueTimeTemperatureAverageMax = valueTimeTemperatureAverageMax;
	}

	public boolean isAlarmToEmail() {
		return alarmToEmail;
	}

	public void setAlarmToEmail(boolean alarmToEmail) {
		this.alarmToEmail = alarmToEmail;
	}

	public String getUserEmail() {
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userB = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);
		User currentUser = userB.getUser();
		userEmail = currentUser.getEmail();
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
