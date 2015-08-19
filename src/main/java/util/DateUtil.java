package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static double dateToTimestamp(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String dateString = format.format(date);
		
		return Double.parseDouble(dateString);
	}
	
	public static String timestampToStringFmt(double datetime) {
		StringBuffer sb = new StringBuffer(String.format("%.0f", datetime));
		System.out.println(sb.toString() + "-------------");
		sb.insert(12, ':');
		sb.insert(10, ':');
		sb.insert(8, ' ');
		sb.insert(6, '-');
		sb.insert(4, '-');
		String formatedDate = sb.toString();
		// "yyyy-MM-dd hh:mm:ss"
		return formatedDate;
	}
	
	public static String toShortDateFormat(double datetime) {
		StringBuffer sbIn = new StringBuffer(String.format("%.0f", datetime));
		StringBuffer sbOut = new StringBuffer();
		sbOut.append(sbIn.substring(6, 7));
		sbOut.append('/');
		sbOut.append(sbIn.substring(4, 5));
		sbOut.append(' ');
		sbOut.append(sbIn.substring(8, 9));
		sbOut.append(':');
		sbOut.append(sbIn.substring(10, 11));
		sbOut.append(':');
		sbOut.append(sbIn.substring(12, 13));
		
		return sbOut.toString();
	}
	
	public static String printTomorrow() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(dt);
	}

	public static String printNextHour(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.HOUR, 1);
		dt = c.getTime();
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dt);
	}
	
	public static Date getYesterday() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, -1);
		
		return c.getTime();
	}
	
}