package dao;

import org.hibernate.Query;
import org.hibernate.Session;

import entity.Group;

public class GroupDAO extends AbstractDAO<Group> {

	public GroupDAO() {
		super(Group.class);
	}

	public Group getGroupByName(final String groupname) {
		Session hibernateSession = this.getSession();
		String hql = "SELECT g FROM Group g WHERE g.groupname = :groupname";
		Query query = hibernateSession.createQuery(hql).setParameter(
				"groupname", groupname);
		return this.findOne(query);
	}

}
