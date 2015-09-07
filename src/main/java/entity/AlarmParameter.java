package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "alarmParameters")
public class AlarmParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "alarmId")
	private Alarm alarm;
	
	@Column(name = "type") 
	private String type;  // types defined in util.AlarmType
	
	@Column(name = "valueMax")
	private double valueMax;
	
	@Column(name = "valueMin")
	private double valueMin;
	
	@Column(name = "timeForMin")
	@Temporal(TemporalType.DATE)
	private Date timeForMin;
	
	@Column(name = "timeForMax")
	@Temporal(TemporalType.DATE)
	private Date timeForMax;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getValueMax() {
		return valueMax;
	}

	public void setValueMax(double valueMax) {
		this.valueMax = valueMax;
	}

	public double getValueMin() {
		return valueMin;
	}

	public void setValueMin(double valueMin) {
		this.valueMin = valueMin;
	}

	public Date getTimeForMin() {
		return timeForMin;
	}

	public void setTimeForMin(Date timeForMin) {
		this.timeForMin = timeForMin;
	}

	public Date getTimeForMax() {
		return timeForMax;
	}

	public void setTimeForMax(Date timeForMax) {
		this.timeForMax = timeForMax;
	}


}
