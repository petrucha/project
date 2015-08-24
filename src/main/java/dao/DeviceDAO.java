package dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.Device;

public class DeviceDAO extends AbstractDAO<Device> {
	
	private static final Logger log = Logger.getLogger(DeviceDAO.class);

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
		log.info("Creating a query: " + hql);
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
		log.info("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);

		return query.list();
	}
	
	/**
	 * @param mac
	 * @return a device with specified MAC Address
	 */
	public Device getDeviceByMac(final String mac) {
		Session hibernateSession = this.getSession();
		String hql = "FROM Device d WHERE d.mac = :mac";
		log.info("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		log.info("Setting a param \"mac\"=" + mac);
		query.setParameter("mac", mac);
		
		return this.findOne(query);
	}

}
