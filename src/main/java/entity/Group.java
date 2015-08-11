package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "groups")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "g_id")
	private int id;

	@Column(name = "groupname")
	private String groupname;

	@Column(name = "groupdesc")
	private String groupdesc;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_group", joinColumns = { @JoinColumn(name = "group_id", referencedColumnName = "g_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "u_id") })
	private Set<User> userGroups;

	public Group() {
		userGroups = new HashSet<User>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupdesc() {
		return groupdesc;
	}

	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}

/*	public void addUserInGroup(User user) {
		if (!getUserRoles().contains(user)) {
			getUserRoles().add(user);
			if (user.getGroup() != null) {
				user.getGroup().getUserRoles().remove(user);
			}
			user.setGroup(this);
		}
	}*/

	public Set<User> getUserRoles() {
		return userGroups;
	}

	public void setUserRoles(Set<User> userGroups) {
		this.userGroups = userGroups;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Group) {
			Group group = (Group) obj;
			return group.getId() == id;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", groupname= " + groupname
				+ ", groupdesc = " + groupdesc + "]";
	}
}
