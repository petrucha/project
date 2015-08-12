package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Group;
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
}
