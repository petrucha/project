package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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

	public Record[] getRecordsArray() {
		List<Record> recordsList = this.getRecords();
		Record[] array = new Record[recordsList.size()];
		return recordsList.toArray(array);

	}

	public Record[] getRecordsByDevice(String device) {
		List<Record> recordsList = new ArrayList<Record>();
		try {
			HibernateUtil.beginTransaction();
			recordsList = recordDAO.getRecordsByDevice(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getRecordsByDevice()");
		}
		Record[] array = new Record[recordsList.size()];
		return recordsList.toArray(array);
	}

	// will be used for selecting a single record
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

	// must have
	public void deleteRecord(Record record) {
		try {
			HibernateUtil.beginTransaction();
			recordDAO.delete(record);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: deleteRecord(" + record.toString() + ")");
		}
	}

	// used for filtering records by device
	public String[] getDevicesArray() {
		List<String> devices = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			devices = recordDAO.getDevicesList();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getDevicesArray()");
		}
		String[] devArray = new String[devices.size()];
		return devices.toArray(devArray);
	}

	// will be used for filtering records by devices and time
	public List<Record[]> getRecordsByDevicesAndTime(String[] devices, double startTime, double endTime) {
		List<Record[]> recordsList = new ArrayList<Record[]>();
		try {
			HibernateUtil.beginTransaction();
			recordsList = recordDAO.getRecordByDevicesAndTime(devices, startTime, endTime);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error:  getRecordsByDevicesAndTime(...)");
		}
		return recordsList;
	}

	// will be used for filtering records by devices and time
	public HashMap<String, Double> getFilteredAverages(String[] devices, double startTime, double endTime) {
		HashMap<String, Double> averages = new HashMap<String, Double>();
		try {
			HibernateUtil.beginTransaction();
			averages = recordDAO.getFilteredAverages(devices, startTime, endTime);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error:  getFilteredAverages(...)");
		}
		return averages;
	}
}