package dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import entity.Group;

public class GroupDAO extends AbstractDAO<Group> {
	
	private static final Logger LOG = Logger.getLogger(GroupDAO.class);

	public GroupDAO() {
		super(Group.class);
	}

	/**
	 * @param groupname
	 * @return group by group name
	 */
	public Group getGroupByName(final String groupname) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT g FROM Group g WHERE g.groupname = :groupname";
		LOG.trace("Creating a query: " + hql);
		Query query = hibernateSession.createQuery(hql);
		LOG.trace("Setting a param \"groupname\"=" + groupname);
		query.setParameter("groupname", groupname);
		
		return this.findOne(query);
	}

}
