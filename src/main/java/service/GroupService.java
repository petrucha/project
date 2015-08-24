package service;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import dao.GroupDAO;
import entity.Group;

public class GroupService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(GroupService.class);
	
	private static GroupDAO groupDAO = new GroupDAO();
	
	private static GroupService instance = null;

	public static GroupService getInstance() {
		if (instance == null) {
			instance = new GroupService();
		}

		return instance;
	}
	
	/**
	 * @param group
	 * @return true if a group is successfully added, else false
	 */
	public boolean addGroup(Group group) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Saving a group: " + group.toString());
			groupDAO.save(group);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to create a group: " + group.toString());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}

	/**
	 * @param groupname
	 * @return a group by it's name 
	 */
	public Group getGroupByName(final String groupname) {
		Group group = null;
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Getting a group with name: " + groupname);
			group = groupDAO.getGroupByName(groupname);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to get a group with name: " + groupname);
			LOG.error(ex.getCause());
		}
		
		return group;
	}
	
	/**
	 * @param group
	 * @return true if a group is successfully deleted, else false
	 */
	public boolean deleteGroup(Group group) {
		try {
			HibernateUtil.beginTransaction();
			LOG.debug("Deleting a group: " + group.toString());
			groupDAO.delete(group);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			LOG.error("Failed to delete a group: " + group.toString());
			LOG.error(ex.getCause());
			HibernateUtil.rollbackTransaction();
		}
		
		return true;
	}

}