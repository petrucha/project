package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.DateUtil;
import util.HibernateUtil;
import dao.RecordDAO;
import entity.Record;

public class RecordService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(RecordService.class);
	
	private static RecordDAO recordDAO = new RecordDAO();
	
	private static RecordService instance = null;

	public static RecordService getInstance() {
		if (instance == null) {
			instance = new RecordService();
		}

		return instance;
	}

	
	/**
	 * @param record
	 * @return true if a record is successfully added, else false
	 */
	public boolean addRecord(Record record) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Saving a record: " + record.toString());
			recordDAO.save(record);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to create a record: " + record.toString());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}
	
	/**
	 * @param id
	 * @return a record by id
	 */
	public Record getRecord(int id) {
		Record record = null;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting a record with id: " + id);
			record = recordDAO.findByID(id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get a record with id: " + id);
			LOG.error(ex.getCause());
		}
		
		return record;
	}

	/**
	 * @return all records
	 */
	@SuppressWarnings("unchecked")
	public List<Record> getRecords() {
		List<Record> recordsList = new ArrayList<Record>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting all records");
			recordsList = recordDAO.findAll();
			LOG.debug("Found: " + recordsList.size());
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get all records");
			LOG.error(ex.getCause());
		}
		
		return recordsList;
	}

	/**
	 * @param record
	 * @return true if a record is successfully deleted, else false
	 */
	public boolean deleteRecord(Record record) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Deleting a record: " + record.toString());
			recordDAO.delete(record);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to delete the record: " + record.toString());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}

	
	/**
	 * @param devices - MAC addresses
	 * @param startTime
	 * @param endTime
	 * @return records by devices and time
	 */
	public List<Record[]> getRecordsForLineChart(String[] devices, String quantity, Date startDate, Date endDate) {
		List<Record[]> recordsList = new ArrayList<Record[]>();
		try {
			HibernateUtil.beginTransaction();
			double startTime = DateUtil.dateToTimestamp(startDate);
			double endTime = DateUtil.dateToTimestamp(endDate);
			LOG.debug("Searching of records of type \"" + quantity
					+ "\" from " + startDate + " to " + endDate);
			recordsList = recordDAO.getRecordsForLineChart(devices, quantity, startTime, endTime);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get records");
			LOG.error(ex.getCause());
		}
		
		return recordsList;
	}

	/**
	 * @param devices - MAC addresses
	 * @param startTime
	 * @param endTime
	 * @return record's averages
	 */
	public HashMap<String, Double> getFilteredAverages(String[] devices, String quantity, Date startDate, Date endDate) {
		HashMap<String, Double> averages = new HashMap<String, Double>();
		try {
			HibernateUtil.beginTransaction();
			double startTime = DateUtil.dateToTimestamp(startDate);
			double endTime = DateUtil.dateToTimestamp(endDate);
			LOG.debug("Searching of averages of type \"" + quantity
					+ "\" from \"" + startDate + "\" to \"" + endDate + "\"");
			averages = recordDAO.getFilteredAverages(devices, quantity, startTime, endTime);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get records");
			LOG.error(ex.getCause());
		}
		
		return averages;
	}

	/**
	 * @param devices - MAC addresses
	 * @param recordsCount
	 * @return last records by devices
	 */
	public List<Record[]> getLastRecords(String[] devices, String quantity, int recordsCount) {
		List<Record[]> recordsList = new ArrayList<Record[]>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Searching of last records of type \"" + quantity
					+ "\". Limit: " + recordsCount);
			recordsList = recordDAO.getLastRecords(devices, quantity, recordsCount);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get records");
			LOG.error(ex.getCause());
		}
		
		return recordsList;
	}
	
}