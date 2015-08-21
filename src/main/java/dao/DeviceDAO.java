package dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.Device;

public class DeviceDAO extends AbstractDAO<Device> {

	public DeviceDAO() {
		super(Device.class);
	}
	
	public List<Device> getAllDevices(final boolean notEmpty) {
		Session hibernateSession = this.getSession();
		String hql = "FROM Device d";
		if (notEmpty) {
			hql += " WHERE d.records IS NOT EMPTY";
		}
		Query query = hibernateSession.createQuery(hql);

		return this.findMany(query);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllMacs(final boolean notEmpty) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT d.mac FROM Device d";
		if (notEmpty) {
			hql += " WHERE d.records IS NOT EMPTY";
		}
		Query query = hibernateSession.createQuery(hql);

		return query.list();
	}
	
	public Device getDeviceByMac(final String mac) {
		Session hibernateSession = this.getSession();
		String hql = "FROM Device d WHERE d.mac = :mac";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("mac", mac);
		
		return this.findOne(query);
	}

}
