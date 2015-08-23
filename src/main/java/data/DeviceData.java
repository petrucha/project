package data;

import java.io.Serializable;

public class DeviceData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int deviceId;
	
	private String mac;
	
	private int recordsCount;
	
	private String lastUpdated = "-";
	
	
	public String getMac() {
		return mac;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public DeviceData() {
		super();
	}

	public DeviceData(String mac, int recordsCount, String lastUpdated) {
		super();
		this.mac = mac;
		this.recordsCount = recordsCount;
		this.lastUpdated = lastUpdated;
	}

	public DeviceData(int deviceId, String mac, int recordsCount) {
		super();
		this.deviceId = deviceId;
		this.mac = mac;
		this.recordsCount = recordsCount;
	}

	@Override
	public String toString() {
		return "DeviceData [mac=" + mac + ", recordsCount=" + recordsCount + ", lastUpdated=" + lastUpdated + "]";
	}

}