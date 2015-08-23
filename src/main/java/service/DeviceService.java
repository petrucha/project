package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import util.DateUtil;
import util.HibernateUtil;
import dao.DeviceDAO;
import dao.RecordDAO;
import dao.UserDAO;
import data.DeviceData;
import entity.Device;
import entity.Record;
import entity.User;

public class DeviceService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static UserDAO userDAO = new UserDAO();
	private static DeviceDAO deviceDAO = new DeviceDAO();
	private static RecordDAO recordDAO = new RecordDAO();
	private static DeviceService instance = null;

	public static DeviceService getInstance() {
		if (instance == null) {
			instance = new DeviceService();
		}

		return instance;
	}
	
	
	public boolean addDevice(Device device) {
		try {
			HibernateUtil.beginTransaction();
			deviceDAO.save(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: addDevice(" + device.toString() + ")");
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}
	
	public Device getDevice(final int id) {
		Device device = null;
		try {
			HibernateUtil.beginTransaction();
			device = deviceDAO.findByID(id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getDevice()");
		}
		
		return device;
	} 
	
	public Device getDeviceByMac(String mac, boolean withRecords) {
		Device device = null;
		try {
			HibernateUtil.beginTransaction();
			device = deviceDAO.getDeviceByMac(mac);
			if (withRecords && !Hibernate.isInitialized(device.getRecords())) {
				Hibernate.initialize(device.getRecords());
			}
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getDevice()");
		}
		
		return device;
	}
	
	public boolean updateDevice(Device device) {
		try {
			HibernateUtil.beginTransaction();
			deviceDAO.merge(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: updateDevice(" + device.toString() + ")");
		}
		
		return true;
	}
	
	public boolean deleteDevice(Device device) {
		try {
			HibernateUtil.beginTransaction();
			deviceDAO.delete(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: deleteRecord(" + device.toString() + ")");
		}
		
		return true;
	}
	
	/**
	 * @param notEmpty
	 * @return all (has at least one record) devices
	 */
	public List<Device> getAllDevices(boolean notEmpty) {
		List<Device> devices = new ArrayList<Device>();
		try {
			HibernateUtil.beginTransaction();
			devices = deviceDAO.getAllDevices(notEmpty);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getAllDevices(" + notEmpty + ")");
		}
		
		return devices;
	}
	
	/**
	 * @param notEmpty
	 * @return all (has at least one record) devices MACs
	 */
	public List<String> getAllMacs(boolean notEmpty) {
		List<String> macs = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			macs = deviceDAO.getAllMacs(notEmpty);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getAllDevices(" + notEmpty + ")");
		}
		
		return macs;
	}
	
	
	/**
	 * @return devices data for DevicesViewBean
	 */
	public List<DeviceData> getDevicesData() {
		List<DeviceData> deviceDatas = new ArrayList<DeviceData>();
		try {
			HibernateUtil.beginTransaction();
			List<Device> devices = deviceDAO.getAllDevices(false);
			for (Device device : devices) {
				int recordsCount = recordDAO.countRecords(device.getId());
				Record record = recordDAO.getLastRecord(device.getId());
				DeviceData dd = new DeviceData(device.getId(), device.getMac(), recordsCount);
				if (record != null) {
					String lastDate = DateUtil.timestampToStringFmt(record.getTimestamp());
					dd.setLastUpdated(lastDate);
				}
				deviceDatas.add(dd);
			}
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getDevicesData()");
			ex.printStackTrace();
		}
		
		return deviceDatas;
	}
	
	public boolean addDeviceToUsers(Device device, List<String> usernames) {
		if (!usernames.isEmpty()){
			try {
			HibernateUtil.beginTransaction();
			List<User> userList = userDAO.getUsersByUsernames(usernames);
			Set<User> userSet = new HashSet<User>(userList);
			device.setUsers(userSet);
				deviceDAO.merge(device);
				HibernateUtil.commitTransaction();
			} catch (HibernateException ex) {
				System.out.println("Error: addDeviceToUsers()");
				ex.printStackTrace();
			}
		}
		
		return true;
	}

}