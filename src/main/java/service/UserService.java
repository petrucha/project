package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import dao.UserDAO;
import entity.User;

public class UserService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(UserService.class);
	
	private static UserDAO userDAO = new UserDAO();
	
	private static UserService instance = null;

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}

		return instance;
	}

	/**
	 * @param username
	 * @param password
	 * @return an user by username and password
	 */
	public User getUserByUsernameAndPassword(String username, String password) {
		User user = null;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting an user with username \"" + username
					+ "\" and password \"" + password + "\"");
			user = userDAO.getUserByUsernameAndPassword(username, password);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get an user with username \"" + username
					+ "\" and password \"" + password + "\"");
			LOG.error(ex.getCause());
		}
		return user;
	}
	
	/**
	 * @param id
	 * @return an user by id
	 */
	public User getUserById(int id) {
		User user = null;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting an user with id: " + id);
			user = userDAO.findByID(id);
			LOG.debug("Initialization of user's devices set");
			if (!Hibernate.isInitialized(user.getDevices())) {
				Hibernate.initialize(user.getDevices());
			}
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get an user with id: " + id);
			LOG.error(ex.getCause());
		}
		return user;
	}

	/**
	 * @param user
	 * @return true if an user is successfully added, else false
	 */
	public boolean addUser(User user) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Saving an user: " + user.getUsername());
			userDAO.save(user);
			HibernateUtil.commitTransaction();
			return true;
		} catch (HibernateException ex) {
			LOG.error("Failed to create an user: " + user.getUsername());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		return false;

	}
	
	/**
	 * @return true if an user is the first, else false
	 */
	public boolean isFirstUser() {
		int numUsers = 0;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Obtaining the number of users");
			numUsers = userDAO.countUsers();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get users number");
			LOG.error(ex.getCause());
		}
		if (numUsers > 0)
			return false;
		return true;
	}

	/**
	 * @return number of all users
	 */
	public int getNumberOfUsers() {
		int numUsers = 0;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Obtaining the number of users");
			numUsers = userDAO.countUsers();
			LOG.debug("Found: " + numUsers + " users.");
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get users number");
			LOG.error(ex.getCause());
		}
		return numUsers;
	}


	/**
	 * @return all users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		List<User> allUsers = new ArrayList<User>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Obtaining of all users");
			allUsers = userDAO.findAll();
			LOG.debug("Found users: " + allUsers.size());
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get all users");
			LOG.error(ex.getCause());
		}
		return allUsers;
	}

	
	/**
	 * @param user
	 * @return  true if an user is successfully deleted, else false
	 */
	public boolean deleteUser(User user) {
		if (user != null) {
			try {
				HibernateUtil.beginTransaction();
				LOG.debug("Getting an user with id: " + user.getId());
				user = userDAO.findByID(user.getId());
				LOG.debug("Deleting of all user's relations");
				user.remove();
				LOG.debug("Deleting of user: " + user.getUsername());
				userDAO.delete(user);
				HibernateUtil.commitTransaction();
				return true;
			} catch (HibernateException ex) {
				LOG.error("Failed to delete the user: " + user.getUsername());
				LOG.error(ex.getCause());
				HibernateUtil.rollbackTransaction();
			}
		}
		
		return false;
	}
	
	/**
	 * @param deviceId
	 * @return usernames of users who has the device
	 */
	public List<String> getUsernamesByDevice(int deviceId) {
		List<String> usernames = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting usernames by device with id: " + deviceId);
			usernames = userDAO.getUsernamesByDevice(deviceId);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get usernames by device with id: " + deviceId);
			LOG.error(ex.getCause());
		}
		
		return usernames;
	}
	
	/**
	 * @param deviceId
	 * @return usernames of users who hasn't the device
	 */
	public List<String> getUsernamesNotHavingDevice(int deviceId) {
		List<String> usernames = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting usernames by absence of the device with id: " + deviceId);
			usernames = userDAO.getUsernamesNotHavingDevice(deviceId);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get by absence of the device with id: " + deviceId);
			LOG.error(ex.getCause());
		}
		
		return usernames;
	}
	
	/**
	 * @return usernames of all users
	 */
	public List<String> getUsernames() {
		List<String> usernames = new ArrayList<String>();
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting all usernames");
			usernames = userDAO.getUsernames();
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get all usernames");
			LOG.error(ex.getCause());
		}
		
		return usernames;
	}
	
	/**
	 * @param username
	 * @return true if user already exist, else false
	 */
	public boolean isUserExist(String username) {
		boolean exist = false;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Checking of existing the user: " + username);
			exist = userDAO.isUserExist(username);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to check existing");
			LOG.error(ex.getCause());
		}
		
		return exist;
	}

}