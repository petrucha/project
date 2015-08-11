package rest.data;

public class RecordData {
	
	private String device;
	private String quantity;
	private int value;
	private double timestamp;

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RecordData: [device = " + device + ", quantity = "
				+ quantity + ", value = " + value + ", timestamp = "
				+ timestamp + "]";
	}
}
