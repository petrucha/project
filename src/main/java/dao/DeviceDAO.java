package dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.Device;

public class DeviceDAO extends AbstractDAO<Device> {
	
	private static final Logger LOG = Logger.getLogger(DeviceDAO.class);

	public DeviceDAO() {
		super(Device.class);
	}
	
	/**
	 * @param notEmpty
	 * @return all devices or those that have at least one record if notEmpty is true
	 */
	public List<Device> getAllDevices(final boolean notEmpty) {
		Session hibernateSession = this.getSession();
		String hql = "FROM Device d";
		if (notEmpty) {
			hql += " WHERE d.records IS NOT EMPTY";
		}
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);

		return this.findMany(query);
	}
	
	/**
	 * @param notEmpty
	 * @return all devices MAC addresses or those that have at least one record if notEmpty is true
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllMacs(final boolean notEmpty) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT d.mac FROM Device d";
		if (notEmpty) {
			hql += " WHERE d.records IS NOT EMPTY";
		}
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);

		return query.list();
	}
	
	/**
	 * @param mac
	 * @return a device with specified MAC address
	 */
	public Device getDeviceByMac(final String mac) {
		Session hibernateSession = this.getSession();
		String hql = "FROM Device d WHERE d.mac = :mac";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting a param \"mac\"=" + mac);
		query.setParameter("mac", mac);
		
		return this.findOne(query);
	}
	
	/**
	 * @param username
	 * @param notEmpty
	 * @return MAC addresses of the user's devices
	 */
	@SuppressWarnings("unchecked")
	public List<String> getMacsByUser(final String username, final boolean notEmpty) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT d.mac FROM Device d "
				+ "LEFT JOIN d.users u "
				+ "WHERE u.username = :username";
		if (notEmpty) {
			hql += " AND d.records IS NOT EMPTY";
		}
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting a param username=\"" + username + "\"");
		query.setParameter("username", username);
		
		return query.list();
	}
	
	/**
	 * @param mac
	 * @return true if a device already exist, else false
	 */
	public boolean isDeviceExist(final String mac) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT d.mac FROM Device d "
				+ "WHERE d.mac = :mac";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting a param mac=\"" + mac + "\"");
		query.setParameter("mac", mac);
		if ( ((String) query.uniqueResult()) != null) {
			LOG.trace("Found a device with MAC: " + mac);
			return true;
		}
		LOG.trace("Device with MAC \"" + mac + "\" not found");
		return false;
	}

}
