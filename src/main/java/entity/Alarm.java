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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "alarms", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Alarm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "parameter")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "alarm")
    private Set<AlarmParameter> alarmParameter = new HashSet<AlarmParameter> ();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<AlarmParameter> getAlarmParameter() {
		return alarmParameter;
	}

	public void setAlarmParameter(Set<AlarmParameter> alarmParameter) {
		this.alarmParameter = alarmParameter;
	}
	
	
}
