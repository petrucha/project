package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;

import util.DateUtil;
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
		try {
			HibernateUtil.beginTransaction();
			recordDAO.save(record);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: addRecord()");
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}
	
	public Record getRecord(int id) {
		Record record = null;
		try {
			HibernateUtil.beginTransaction();
			record = recordDAO.findByID(id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getRecord()");
			HibernateUtil.rollbackTransaction();
		}
		
		return record;
	}

	@SuppressWarnings("unchecked")
	public List<Record> getRecords() {
		List<Record> recordsList = new ArrayList<Record>();
		try {
			HibernateUtil.beginTransaction();
			recordsList = recordDAO.findAll();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error:  getRecords()");
		}
		
		return recordsList;
	}

	public boolean deleteRecord(Record record) {
		try {
			HibernateUtil.beginTransaction();
			recordDAO.delete(record);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: deleteRecord(" + record.toString() + ")");
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}

	
	/**
	 * @param devices
	 * @param startTime
	 * @param endTime
	 * @return records by devices and time
	 */
	public List<Record[]> getRecordsForLineChart(String[] devices, String quantity, Date startDate, Date endDate) {
		List<Record[]> recordsList = new ArrayList<Record[]>();
		double startTime = DateUtil.dateToTimestamp(startDate);
		double endTime = DateUtil.dateToTimestamp(endDate);
		try {
			HibernateUtil.beginTransaction();
			recordsList = recordDAO.getRecordsForLineChart(devices, quantity, startTime, endTime);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error:  getRecordsByDevicesAndTime(...)");
		}
		
		return recordsList;
	}

	/**
	 * @param devices
	 * @param startTime
	 * @param endTime
	 * @return record's averages
	 */
	public HashMap<String, Double> getFilteredAverages(String[] devices, String quantity, Date startDate, Date endDate) {
		HashMap<String, Double> averages = new HashMap<String, Double>();
		double startTime = DateUtil.dateToTimestamp(startDate);
		double endTime = DateUtil.dateToTimestamp(endDate);
		try {
			HibernateUtil.beginTransaction();
			averages = recordDAO.getFilteredAverages(devices, quantity, startTime, endTime);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error:  getFilteredAverages(...)");
		}
		
		return averages;
	}

	/**
	 * @param devices 
	 * @param recordsCount
	 * @return last records by devices
	 */
	public List<Record[]> getLastRecords(String[] devices, String quantity, int recordsCount) {
		List<Record[]> recordsList = new ArrayList<Record[]>();
		try {
			HibernateUtil.beginTransaction();
			recordsList = recordDAO.getLastRecords(devices, quantity, recordsCount);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error:  getLastRecords(...)");
		}
		
		return recordsList;
	}
	
}