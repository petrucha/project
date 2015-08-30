package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@OneToMany(mappedBy = "group")
	private Set<User> users =  new HashSet<User>(0);
	
	
	public Group(String groupname, String groupdesc) {
		super();
		this.groupname = groupname;
		this.groupdesc = groupdesc;
	}
	
	public Group() {
	
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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
