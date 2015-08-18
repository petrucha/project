package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Record;

public class RecordDAO extends AbstractDAO<Record> {

	public RecordDAO() {
		super(Record.class);

	}
	
	/**
	 * @param macs
	 * @param startTime
	 * @param endTime
	 * @return records by time and devices
	 */
	public List<Record[]> getRecordByDevicesAndTime(final String[] macs,
												  final double startTime, 
												  final double endTime) {
		List<Record[]> sortedRecords = new ArrayList<Record[]>();
		if (macs.length == 0) {
			sortedRecords.add(new Record[0]);
			return sortedRecords;
		}
		
		Session hibernateSession = this.getSession();
		String hql = "FROM Record r WHERE "
				+ "(r.timestamp BETWEEN :startTime and :endTime) "
				+ "AND (r.device.mac = :mac)";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime);
		//dividing records by device
		for (String mac : macs) {
			query.setParameter("mac", mac);
			List<Record> recordList = this.findMany(query);
			Record[] deviceRecords = new Record[recordList.size()];
			sortedRecords.add(recordList.toArray(deviceRecords));
		}
		return sortedRecords;
	}
	
	
	/**
	 * @param macs
	 * @param startTime
	 * @param endTime
	 * @return record averages by time and devices
	 */
	public HashMap<String, Double> getFilteredAverages(final String[] macs,
												final double startTime,
												final double endTime) {
		HashMap<String, Double> averages = new HashMap<String, Double>();
		if (macs.length == 0) {
			return averages;
		}

		Session hibernateSession = this.getSession();
		String hql = "SELECT AVG(r.value) FROM Record r "
				+ "WHERE (r.timestamp BETWEEN :startTime and :endTime) "
				+ "AND (r.device.mac = :mac)";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime);
		// dividing records by device
		for (String mac : macs) {
			query.setParameter("mac", mac);
			Double average = (Double) query.uniqueResult();
			averages.put(mac, average);
		}

		return averages;
	}
	
	
	/**
	 * @param macs
	 * @param recordsCount
	 * @return last records
	 */
	public List<Record[]> getLastRecords(final String[] macs, final int recordsCount) {
		List<Record[]> lastRecords = new ArrayList<Record[]>();
		if (macs.length == 0) {
			lastRecords.add(new Record[0]);
			return lastRecords;
		}
		
		Session hibernateSession = this.getSession();
		String hql = "SELECT r FROM Record r WHERE r.device.mac = :mac ORDER BY r.timestamp DESC";
		Query query = hibernateSession.createQuery(hql).setMaxResults(recordsCount);
		//dividing records by device
		for (String mac : macs) {
			query.setParameter("mac", mac);
			List<Record> recordList = this.findMany(query);
			Record[] deviceRecords = new Record[recordList.size()];
			lastRecords.add(recordList.toArray(deviceRecords));
		}
		
		return lastRecords;
	}
}
