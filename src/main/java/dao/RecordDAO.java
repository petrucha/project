package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.Record;

public class RecordDAO extends AbstractDAO<Record> {
	
	private static final Logger LOG = Logger.getLogger(RecordDAO.class);

	public RecordDAO() {
		super(Record.class);
	}
	
	/**
	 * @param macs - devices MAC addresses
	 * @param startTime
	 * @param endTime
	 * @return records by time and devices
	 */
	public List<Record[]> getRecordsForLineChart(final String[] macs,
												final String quantity,
												final double startTime, 
												final double endTime) {
		List<Record[]> sortedRecords = new ArrayList<Record[]>();
		if (macs.length == 0) {
			sortedRecords.add(new Record[0]);
			LOG.trace("Returning an empty list");
			return sortedRecords;
		}
		
		Session hibernateSession = this.getSession();
		String hql = "FROM Record r WHERE "
				+ "(r.timestamp BETWEEN :startTime and :endTime) "
				+ "AND r.device.mac = :mac "
				+ "AND r.quantity = :quantity "
				+ "ORDER BY r.timestamp ASC";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting params:"
				+ " \"quantity\"=" + quantity
				+ " \"startTime\"=" + startTime
				+ " \"endTime\"=" + endTime);
		query.setParameter("quantity", quantity)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime);
		//dividing records by device
		for (String mac : macs) {
			LOG.trace("Setting a param \"mac\"=" + mac);
			query.setParameter("mac", mac);
			LOG.trace("Searching for a list of records by device with MAC: " + mac);
			List<Record> recordList = this.findMany(query);
			Record[] deviceRecords = new Record[recordList.size()];
			sortedRecords.add(recordList.toArray(deviceRecords));
		}
		
		return sortedRecords;
	}
	
	
	/**
	 * @param macs - devices MAC addresses
	 * @param startTime
	 * @param endTime
	 * @return record averages by time and devices
	 */
	public HashMap<String, Double> getFilteredAverages(final String[] macs,
												final String quantity,
												final double startTime,
												final double endTime) {
		HashMap<String, Double> averages = new HashMap<String, Double>();
		if (macs.length == 0) {
			LOG.trace("Returning an empty map");
			return averages;
		}

		Session hibernateSession = this.getSession();
		String hql = "SELECT AVG(r.value) AS aver FROM Record r "
				+ "WHERE (r.timestamp BETWEEN :startTime and :endTime) "
				+ "AND r.device.mac = :mac "
				+ "AND r.quantity = :quantity";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting params:"
				+ " \"quantity\"=" + quantity
				+ " \"startTime\"=" + startTime
				+ " \"endTime\"=" + endTime);
		query.setParameter("quantity", quantity)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime);
		// dividing records by device
		for (String mac : macs) {
			LOG.trace("Setting a param \"mac\"=" + mac);
			query.setParameter("mac", mac);
			LOG.trace("Searching for an average value of device with MAC: " + mac);
			Double average =  (Double) query.uniqueResult();
			if (average != null) {
				averages.put(mac, average);
			} else {
				LOG.trace("No found records of device with MAC: " + mac);
			}
		}

		return averages;
	}
	
	
	/**
	 * @param macs - devices MAC addresses
	 * @param recordsCount
	 * @return last records limited by recordsCount
	 */
	public List<Record[]> getLastRecords(final String[] macs, final String quantity, final int recordsCount) {
		List<Record[]> lastRecords = new ArrayList<Record[]>();
		if (macs.length == 0) {
			lastRecords.add(new Record[0]);
			LOG.trace("Returning an empty list");
			return lastRecords;
		}
		
		Session hibernateSession = this.getSession();
		String hql = "FROM Record r WHERE r.device.mac = :mac "
				+ "AND r.quantity = :quantity "
				+ "ORDER BY r.timestamp ASC";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting param:"
				+ " \"quantity\"=" + quantity
				+ " and limit - " + recordsCount);
		query.setParameter("quantity", quantity)
				.setMaxResults(recordsCount);
		//dividing records by device
		for (String mac : macs) {
			LOG.trace("Setting a param \"mac\"=" + mac);
			query.setParameter("mac", mac);
			LOG.trace("Searching for a list of records by device with MAC: " + mac);
			List<Record> recordList = this.findMany(query);
			Record[] deviceRecords = new Record[recordList.size()];
			lastRecords.add(recordList.toArray(deviceRecords));
		}
		
		return lastRecords;
	}
	
	/**
	 * @param deviceId
	 * @return last record of the device
	 */
	public Record getLastRecord(final int deviceId) {
		Session hibernateSession = this.getSession();
		String hql = "FROM Record r WHERE r.device.id = :id "
				+ "ORDER BY r.timestamp DESC";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting param:"
				+ " \"id\"=" + deviceId
				+ " and limit - 1");
		query.setParameter("id", deviceId)
				.setMaxResults(1);
		
		return this.findOne(query);
	}
	
	/**
	 * @param deviceId
	 * @return device's records count
	 */
	public int countRecords(final int deviceId) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT COUNT(r.id) FROM Record r "
				+ "WHERE r.device.id = :id";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting param: \"id\"=" + deviceId);
		query.setParameter("id", deviceId);
		
		return ((Number) query.uniqueResult()).intValue();
	}
	
}
