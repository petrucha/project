package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import util.HibernateUtil;
import dao.DeviceDAO;
import entity.Device;

public class DeviceService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static DeviceDAO deviceDAO = new DeviceDAO();
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
			System.out.println("Error: getDeviceById()");
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
	

}