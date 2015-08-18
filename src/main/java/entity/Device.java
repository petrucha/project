package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@Column(name = "d_id")
	private int id;
	
	@Column(name = "mac")
	private String mac;
	
	@OneToMany(mappedBy = "device") // do change (cascade type)
	private Set<Record> records = new HashSet<Record>(0);
	
	@ManyToMany(mappedBy = "devices")
	private Set<User> users = new HashSet<User>(0);
	
	
	public Device(String mac) {
		super();
		this.mac = mac;
	}

	public Device() {
		super();
	}
	
	
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
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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

}
