package dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import util.HibernateUtil;

public abstract class AbstractDAO<T> {
	private Class<T> entityClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractDAO(Class entityClass) {
		this.entityClass = entityClass;
	}

	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	public void save(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.saveOrUpdate(entity);
	}

	public void merge(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.merge(entity);
	}

	public void delete(T entity) {
		Session hibernateSession = this.getSession();
		hibernateSession.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> findMany(Query query) {
		List<T> t;
		t = (List<T>) query.list();
		return t;
	}

	@SuppressWarnings("unchecked")
	public T findOne(Query query) {
		T t;
		t = (T) query.uniqueResult();
		return t;
	}


	@SuppressWarnings("unchecked")
	public T findByID(int entityId) {
		Session hibernateSession = this.getSession();
		T t = null;
		t = (T) hibernateSession.get(entityClass, entityId);
		return t;
	}

	@SuppressWarnings("rawtypes")
	public List findAll() {
		Session hibernateSession = this.getSession();
		List T = null;
		Query query = hibernateSession.createQuery("from " + entityClass.getName());
		T = query.list();
		return T;
	}
	
	
	
}