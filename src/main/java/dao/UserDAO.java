package dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.User;

public class UserDAO extends AbstractDAO<User> {
	
	private static final Logger LOG = Logger.getLogger(UserDAO.class);

	public UserDAO() {
		super(User.class);
	}

	/**
	 * @param username
	 * @param password
	 * @return an user by username and password
	 */
	public User getUserByUsernameAndPassword(final String username, final String password) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting params:"
				+ " \"username\"=" + username
				+ " \"password\"=" + password);
		query.setParameter("username", username)
				.setParameter("password", password);
		
		return this.findOne(query);
	}

	/**
	 * @return number of all users
	 */
	public int countUsers() {
		Session hibernateSession = this.getSession();
		String hql = "SELECT count(u.id) FROM User u";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		return ((Number) query.uniqueResult()).intValue();

	}
	
	/**
	 * @param deviceId
	 * @return usernames of users who has the device
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUsernamesByDevice(final int deviceId) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT u.username FROM User u "
				+ "LEFT JOIN u.devices d WHERE d.id = :id "
				+ "GROUP BY u.username";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting a param \"id\"=" + deviceId);
		query.setParameter("id", deviceId);
		
		return query.list();
	}
	
	/**
	 * @param deviceId
	 * @return usernames of users who hasn't the device
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUsernamesNotHavingDevice(final int deviceId){
		Session hibernateSession = this.getSession();
		String hql = "SELECT u.username FROM User u "
				+ "LEFT JOIN u.devices d "
				+ "WHERE d.id NOT IN (:id) "
				+ "OR d.id IS NULL "
				+ "GROUP BY u.username";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting a param \"id\"=" + deviceId);
		query.setParameter("id", deviceId);
		
		return query.list();
	}

	/**
	 * @param usernames
	 * @return users by usernames
	 */
	public List<User> getUsersByUsernames(final List<String> usernames) {
		Session hibernateSession = this.getSession();
		String hql = "FROM User u WHERE u.username IN (:names)";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql).setParameterList("names", usernames);
		
		return this.findMany(query);
	}
	
	/**
	 * @return usernames of all users
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUsernames() {
		Session hibernateSession = this.getSession();
		String hql = "SELECT u.username FROM User u";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		
		return query.list();
	}
}
