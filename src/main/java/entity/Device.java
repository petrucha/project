package entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "devices", 
uniqueConstraints = @UniqueConstraint(columnNames = "mac"))
public class Device implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "device_id")
	private int id;
	
	@Column(name = "mac")
	private String mac;
	
	@OneToMany(mappedBy = "device", cascade = CascadeType.ALL) // do change
	private Set<Record> records;


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Set<Record> getRecords() {
		return records;
	}

	public void setRecords(Set<Record> records) {
		this.records = records;
	}
	
	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Device) {
			Device device = (Device) obj;
			return device.getId() == this.id;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", mac=" + mac + "]";
	}

	public Device(String mac) {
		super();
		this.mac = mac;
	}

	public Device() {
		super();
	}
	
}
