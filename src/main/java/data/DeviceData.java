package data;

public class DeviceData {
	
	private String mac;
	
	private int recordsCount;
	
	private String lastUpdated = "-";

	public String getMac() {
		return mac;
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

	public DeviceData(String mac, int recordsCount) {
		super();
		this.mac = mac;
		this.recordsCount = recordsCount;
	}

	@Override
	public String toString() {
		return "DeviceData [mac=" + mac + ", recordsCount=" + recordsCount + ", lastUpdated=" + lastUpdated + "]";
	}

}