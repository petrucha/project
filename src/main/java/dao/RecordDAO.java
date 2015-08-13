package dao;

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
	
	public List<Record> getRecordsByTimestamp(final double startTime, final double endTime) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT r FROM Record r WHERE r.timestamp BETWEEN :startTime and :endTime";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime);
		return this.findMany(query);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDevicesList() {
		Session hibernateSession = this.getSession();
		String hql = "SELECT r.device FROM Record r GROUP BY device";
		Query query = hibernateSession.createQuery(hql);
		return query.list();
	}
}
