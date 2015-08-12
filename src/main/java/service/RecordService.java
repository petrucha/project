package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import util.HibernateUtil;
import dao.RecordDAO;
import entity.Record;

public class RecordService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static RecordDAO recordDAO = new RecordDAO();
	private static RecordService instance = null;
		
	public static RecordService getInstance() {
		if (instance == null) {
			instance = new RecordService();
		}

		return instance;
	}
	
	public boolean addRecord(Record record) {
		try{
			HibernateUtil.beginTransaction();
			recordDAO.save(record);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
        System.out.println("Error: addRecord()");
        HibernateUtil.rollbackTransaction();
    }
		return true;

	}
	@SuppressWarnings("unchecked")
	public List<Record> getRecords() {		
		List<Record> recordsList = new ArrayList<Record>();
		try{
			HibernateUtil.beginTransaction();
			recordsList = recordDAO.findAll();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
	        System.out.println("Error:  getRecords()");
	    }
		return recordsList;
	}
	
	public Record[] getRecordsArray() {
		List<Record> recordsList = this.getRecords();
		Record[] array = new Record[recordsList.size()];
		return recordsList.toArray(array);
		
	}

	public Record[] getRecordsByDevice(String device) {
		List<Record> recordsList = new ArrayList<Record>();
		try{
			HibernateUtil.beginTransaction();
			recordsList =  recordDAO.getRecordsByDevice(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
	        System.out.println("Error: getRecordsByDevice()");
	    }
		Record[] array = new Record[recordsList.size()];
		return recordsList.toArray(array);
	}
	
	public Record getRecordById(int id) {
		Record record = null;
		try {
			HibernateUtil.beginTransaction();
			record = recordDAO.getRecordById(id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getRecordById()");
		}
		
		return record;
	}
	
	public void deleteRecord(Record record) {
		try {
			HibernateUtil.beginTransaction();
			recordDAO.delete(record);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: deleteRecord()");
		}
	}
}