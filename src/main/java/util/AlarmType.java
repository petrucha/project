package util;

import java.util.ArrayList;

public class AlarmType {
	public static final String TEMPERATURE = "Temperature";
	public static final String HUMIDITY = "Humidity";
	public static final String PRESSURE = "Pressure";
	
	public static ArrayList<String> getAlarmTypes() {
		ArrayList<String> alarmTypes = new ArrayList<String>();
		alarmTypes.add(TEMPERATURE);
		alarmTypes.add(HUMIDITY);
		alarmTypes.add(PRESSURE);
		return alarmTypes;
	}
}

