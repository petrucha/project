package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Device;

public class DeviceDAO extends AbstractDAO<Device> {

	public DeviceDAO() {
		super(Device.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Device> getAllDevices(boolean notEmpty) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT d FROM Device d";
		if (notEmpty) {
			hql += " INNER JOIN d.records";
		}
		Query query = hibernateSession.createQuery(hql);
		return query.list();
	}

}
