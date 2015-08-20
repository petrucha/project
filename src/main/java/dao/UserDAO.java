package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.User;

public class UserDAO extends AbstractDAO<User> {

	public UserDAO() {
		super(User.class);
	}

	public User getUserByUsernameAndPassword(final String username, final String password) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
		Query query = hibernateSession.createQuery(hql).setParameter("username", username).setParameter("password", password);
		return this.findOne(query);

	}

	public int countUsers() {
		Session hibernateSession = this.getSession();
		String hql = "SELECT count(u.id) FROM User u";
		Query query = hibernateSession.createQuery(hql);
		return ((Number) query.uniqueResult()).intValue();

	}
	
	@SuppressWarnings("unchecked")
	public List<String> getUsernamesByDevice(final int deviceId) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT u.username FROM User u JOIN u.devices d WHERE d.id = :id";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("id", deviceId);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getUsernamesNotHavingDevice(final int deviceId){
		Session hibernateSession = this.getSession();
		String hql = "SELECT u.username FROM User u "
				+ "LEFT JOIN u.devices d "
				+ "WHERE d.id NOT IN (:id) "
				+ "OR d.id IS NULL";
		Query query = hibernateSession.createQuery(hql)
				.setParameter("id", deviceId);
		
		return query.list();
	}

}
