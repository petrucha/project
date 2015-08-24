package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import dao.DeviceDAO;
import dao.UserDAO;
import entity.User;

public class UserService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static UserDAO userDAO = new UserDAO();
	private static DeviceDAO deviceDAO = new DeviceDAO();
	private static UserService instance = null;

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}

		return instance;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		User user = null;
		try {
			HibernateUtil.beginTransaction();
			user = userDAO.getUserByUsernameAndPassword(username, password);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getUserByUsernameAndPassword()");
		}
		return user;
	}
	
	public User getUserById(int id) {
		User user = null;
		try {
			HibernateUtil.beginTransaction();
			user = userDAO.findByID(id);
			if (!Hibernate.isInitialized(user.getDevices())) {
				Hibernate.initialize(user.getDevices());
			}
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getUserById(" + id + ")");
		}
		return user;
	}

	public boolean addUser(User user) {
		boolean result = true;
		try {
			HibernateUtil.beginTransaction();
			userDAO.save(user);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: addUser()");
			HibernateUtil.rollbackTransaction();
			ex.printStackTrace();
			result = false;
		}
		return result;

	}
	
	public boolean isFirstUser() {
		int numUsers = 0;
		try {
			HibernateUtil.beginTransaction();
			numUsers = userDAO.countUsers();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: isFirstUser()");
		}
		if (numUsers > 0)
			return false;
		return true;
	}

	public int getNumberOfUsers() {
		int numUsers = 0;
		try {
			HibernateUtil.beginTransaction();
			numUsers = userDAO.countUsers();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getNumberOfUsers()");
		}
		return numUsers;
	}


	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		List<User> allUsers = new ArrayList<User>();
		try {
			HibernateUtil.beginTransaction();
			allUsers = userDAO.findAll();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getAllUsers()");
		}
		return allUsers;
	}

	public void deleteUser(User user) {
		if (user != null)
			try {
				HibernateUtil.beginTransaction();
				user = userDAO.findByID(user.getId());
				user.setGroup(null);
				user.remove();
				userDAO.delete(user);
				HibernateUtil.commitTransaction();
			} catch (HibernateException ex) {
				System.out.println("Error: deleteUser()");
				ex.printStackTrace();
				HibernateUtil.rollbackTransaction();
			}
	}
	
	public List<String> getUsernamesByDevice(int deviceId) {
		List<String> usernames = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			usernames = userDAO.getUsernamesByDevice(deviceId);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getUsernamesByDevice()");
		}
		return usernames;
	}
	
	public List<String> getUsernamesNotHavingDevice(int deviceId) {
		List<String> usernames = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			usernames = userDAO.getUsernamesNotHavingDevice(deviceId);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getUsernamesNotHavingDevice()");
		}
		return usernames;
	}
	
	public List<String> getUsernames() {
		List<String> usernames = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			usernames = userDAO.getUsernames();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getUsernames()");
		}
		return usernames;
	}

}