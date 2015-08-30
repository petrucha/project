package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtil {
	
	private static final Logger LOG = Logger.getLogger(DateUtil.class);
	
	/**
	 * @param date
	 * @return for example: 20150826105412
	 */
	public static double dateToTimestamp(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = format.format(date);
		double timestamp = Double.parseDouble(dateString);
		LOG.debug("Date was formated from " + date + " to " + timestamp);
		return timestamp;
	}
	
	/**
	 * @param datetime
	 * @return timestamp in java.util.Date format
	 */
	public static Date timestampToDate(double datetime) {
		String rawDateStr = String.format("%.0f", datetime);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date date = format.parse(rawDateStr);
			LOG.debug("Date was formated from " + datetime + " to " + date);
			return date;
		} catch (ParseException ex) {
			LOG.error("Failed to parse date: " + rawDateStr);
			LOG.error(ex.getCause());
		}
		
		return null;
	}
	
	/**
	 * @param datetime
	 * @return for example: 2015-08-26 10:54:12
	 */
	public static String timestampToStringFmt(double datetime) {
		String rawDateStr = String.format("%.0f", datetime);
		StringBuffer sb = new StringBuffer(rawDateStr);
		sb.insert(12, ':');
		sb.insert(10, ':');
		sb.insert(8, ' ');
		sb.insert(6, '-');
		sb.insert(4, '-');
		String formatedDate = sb.toString();
		LOG.debug("Date was formated from " + datetime + " to '" + formatedDate + "'");
		return formatedDate;
	}
	
	/**
	 * @param datetime
	 * @return for example: 26/08 10:54:12
	 */
	public static String toShortDateFormat(double datetime) {
		StringBuffer sbIn = new StringBuffer(String.format("%.0f", datetime));
		StringBuffer sbOut = new StringBuffer();
		sbOut.append(sbIn.substring(6, 8));
		sbOut.append('/');
		sbOut.append(sbIn.substring(4, 6));
		sbOut.append(' ');
		sbOut.append(sbIn.substring(8, 10));
		sbOut.append(':');
		sbOut.append(sbIn.substring(10, 12));
		sbOut.append(':');
		sbOut.append(sbIn.substring(12, 14));
		String formatedDate = sbOut.toString();
		LOG.debug("Date was formated from " + datetime + " to '" + formatedDate + "'");
		return formatedDate;
	}
	
	/**
	 * @return tomorrow date since now in string format.
	 *  For example: 2015-08-27 10:54:12
	 */
	public static String printTomorrow() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		String tommorowStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt);
		LOG.debug("Got tomorrow date in String: '" + tommorowStr + "'");
		return tommorowStr;
	}

	/**
	 * @param dt
	 * @return next hour since dt in string format.
	 * For example: 2015-08-26 11:54:12
	 */
	public static String printNextHour(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.HOUR, 1);
		dt = c.getTime();
		String nextHourStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt);
		LOG.debug("Got next hour in String: '" + nextHourStr + "'");
		return nextHourStr;
	}
	
	/**
	 * @return yesterday relatively now in java.util.Date format.
	 */
	public static Date getYesterday() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, -1);
		dt = c.getTime();
		LOG.debug("Got date of yesterday: '" + dt + "'");
		return dt;
	}
	
	/**
	 * @return last minute relatively now in java.util.Date format.
	 */
	public static Date getLastMinute() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MINUTE, -1);
		dt = c.getTime();
		LOG.debug("Got last MINUTE: '" + dt + "'");
		return dt;
	}
	
	/**
	 * @return three months ago relatively now in java.util.Date format.
	 */
	public static Date getThreeMonthsAgo() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, -3);
		dt = c.getTime();
		LOG.debug("Got date of three months ago: '" + dt + "'");
		return dt;
	}
	
}