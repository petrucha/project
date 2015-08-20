package service;

import java.io.Serializable;

import org.hibernate.HibernateException;

import util.HibernateUtil;
import dao.GroupDAO;
import entity.Group;

public class GroupService implements Serializable {

	private static final long serialVersionUID = 1L;
	private static GroupDAO groupDAO = new GroupDAO();
	private static GroupService instance = null;

	public static GroupService getInstance() {
		if (instance == null) {
			instance = new GroupService();
		}

		return instance;
	}
	
	public boolean addGroup(Group group) {
		try {
			HibernateUtil.beginTransaction();
			groupDAO.save(group);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: addGroup(" + group + ")");
		}
		
		return true;
	}

	public Group getGroupByName(final String groupname) {
		Group group = null;
		try {
			HibernateUtil.beginTransaction();
			group = groupDAO.getGroupByName(groupname);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: getGroupByName()");
		}
		
		return group;
	}
	
	public boolean deleteGroup(Group group) {
		try {
			HibernateUtil.beginTransaction();
			groupDAO.delete(group);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Error: deleteGroup(" + group + ")");
		}
		
		return true;
	}

}