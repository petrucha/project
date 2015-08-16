package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Record;

public class RecordDAO extends AbstractDAO<Record> {

	public RecordDAO() {
		super(Record.class);

	}

	public List<Record> getRecordsByDevice(final String device) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT r FROM Record r WHERE r.device = :device";
		Query query = hibernateSession.createQuery(hql);
		return this.findMany(query);
	}
	
	public Record getRecordById(final int id) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT r FROM Record r WHERE r.id = :record_id";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("record_id", id);
		
		return this.findOne(query);
	}
	
	public List<Record[]> getRecordByDevicesAndTime(final String[] devices,
												  final double startTime, 
												  final double endTime) {
		List<Record[]> sortedRecords = new ArrayList<Record[]>();
		if (devices.length == 0) {
			return sortedRecords;
		}
		
		Session hibernateSession = this.getSession();
		String hql = "SELECT r FROM Record r WHERE (r.timestamp BETWEEN :startTime and :endTime) AND (r.device = :device)";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime);
		//dividing records by device
		for (int i = 0; i < devices.length; i++) {
			query.setParameter("device", devices[i]);
			List<Record> recordList = this.findMany(query);
			Record[] deviceRecords = new Record[recordList.size()];
			sortedRecords.add(recordList.toArray(deviceRecords));
		}
		
		return sortedRecords;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDevicesList() {
		Session hibernateSession = this.getSession();
		String hql = "SELECT r.device FROM Record r GROUP BY device";
		Query query = hibernateSession.createQuery(hql);
		return query.list();
	}
}
