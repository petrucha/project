package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import util.DateUtil;
import util.HibernateUtil;
import util.Role;
import dao.DeviceDAO;
import dao.RecordDAO;
import dao.UserDAO;
import data.DeviceData;
import entity.Device;
import entity.Record;
import entity.User;

public class DeviceService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(DeviceService.class);
	
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
	
	/**
	 * @param device
	 * @return true if a device is successfully added, else false
	 */
	public boolean addDevice(Device device) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Saving a device: " + device.toString());
			deviceDAO.save(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to create a device: " + device.toString());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}
	
	/**
	 * @param id
	 * @return a device by id
	 */
	public Device getDevice(final int id) {
		Device device = null;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting a device with id: " + id);
			device = deviceDAO.findByID(id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get a device with id: " + id);
			LOG.error(ex.getCause());
		}

		return device;
	} 
	
	/**
	 * @param mac
	 * @param withRecords
	 * @return device by MAC Address and initialized records when withRecords is true
	 */
	public Device getDeviceByMac(String mac, boolean withRecords) {
		Device device = null;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting a device with MAC: " + mac);
			device = deviceDAO.getDeviceByMac(mac);
			if (withRecords && !Hibernate.isInitialized(device.getRecords())) {
				LOG.debug("Initializing records of the device: " + device.toString());
				Hibernate.initialize(device.getRecords());
			}
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get a device with MAC: " + mac);
			LOG.error(ex.getCause());
		}
		
		return device;
	}
	
	/**
	 * @param device
	 * @return true if a device is successfully updated, else false
	 */
	public boolean updateDevice(Device device) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Merging a device: " + device.toString());
			deviceDAO.merge(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to update a device: " + device.toString());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}
	
	/**
	 * @param device
	 * @return true if a device is successfully deleted, else false
	 */
	public boolean deleteDevice(Device device) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Deleting a device: " + device.toString());
			deviceDAO.delete(device);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to delete a device: " + device.toString());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}
	
	/**
	 * @param notEmpty
	 * @return all devices or those that have at least one record if notEmpty is true
	 */
	public List<Device> getAllDevices(boolean notEmpty) {
		List<Device> devices = new ArrayList<Device>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting all devices. \"notEmpty\" is: " + notEmpty);
			devices = deviceDAO.getAllDevices(notEmpty);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get all devices. \"notEmpty\" is: " + notEmpty);
			LOG.error(ex.getCause());
		}
		
		return devices;
	}
	
	/**
	 * @param notEmpty
	 * @return all devices MAC addresses or those that have at least one record if notEmpty is true
	 */
	public List<String> getAllMacs(boolean notEmpty) {
		List<String> macs = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting all devices MACs. \"notEmpty\" is: " + notEmpty);
			macs = deviceDAO.getAllMacs(notEmpty);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get all devices MACs. \"notEmpty\" is: " + notEmpty);
			LOG.error(ex.getCause());
		}
		
		return macs;
	}
	
	
	/**
	 * @return devices data for DevicesViewBean
	 * returns all devices data if the user has ADMINISTRATOR role
	 * or user's devices data if hasn't
	 */
	public List<DeviceData> getUserDevicesData(User user) {
		List<DeviceData> deviceDatas = new ArrayList<DeviceData>();
		try {
			HibernateUtil.beginTransaction();
			List<Device> devices;
			LOG.debug("Checking of a user's role");
			if ( (user.getGroup().getGroupname()).equals(Role.ADMINISTRATOR) ) {
				 LOG.debug("Getting all devices");
				 devices = deviceDAO.getAllDevices(false);
			} else {
				// re-initialization of user's devices
				LOG.debug("Getting the user by id: " + user.getId());
				user = userDAO.findByID(user.getId());
				LOG.debug("Getting the user's devices");
				devices = new ArrayList<Device>(user.getDevices());
			}
			LOG.debug("Creating devices data");
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
			LOG.error("Failed to get devices data for user: " + user.getUsername());
			LOG.error(ex.getCause());
		}
		
		return deviceDatas;
	}
	
	/**
	 * @param device
	 * @param usernames 
	 * @return true if a device is successfully linked with users, else false
	 */
	public boolean addDeviceToUsers(Device device, List<String> usernames) {
		if (!usernames.isEmpty()){
			try {
				HibernateUtil.beginTransaction();
				LOG.debug("Getting users by usernames");
				List<User> userList = userDAO.getUsersByUsernames(usernames);
				Set<User> userSet = new HashSet<User>(userList);
				device.setUsers(userSet);
				LOG.debug("Merging a device: " + device.toString());
				deviceDAO.merge(device);
				HibernateUtil.commitTransaction();
			} catch (HibernateException ex) {
				LOG.error("Failed to link users and the device: " + device.toString());
				LOG.error(ex.getCause());
				HibernateUtil.rollbackTransaction();
			}
		}
		
		return true;
	}

}