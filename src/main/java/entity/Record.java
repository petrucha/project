package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "records")
public class Record  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "device_id")
	private Device device; 

	@Column(name = "quantity")
	private String quantity;

	@Column(name = "value")
	private int value;

	@Column(name = "timestamp")
	private double timestamp; 
	
	
	public Record() {
		super();
	}

	public Record(Device device, String quantity, int value, double timestamp) {
		super();
		this.device = device;
		this.quantity = quantity;
		this.value = value;
		this.timestamp = timestamp;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
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

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Record) {
			Record record = (Record) obj;
			return record.getId() == id;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", device=" + device + ", quantity=" + quantity + ", value=" + value
				+ ", timestamp=" + timestamp + "]";
	}

}
