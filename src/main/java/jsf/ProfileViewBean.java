package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import entity.User;
import service.UserService;
import util.Role;


public class ProfileViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static  UserService userService =  UserService.getInstance();
	private List<User> users = new ArrayList<User>();
	private List<String> groups = new ArrayList<String>();
	private List<User> filteredProfiles = new ArrayList<User>();
	private User selectedUser;
	
	public ProfileViewBean() {}
	
	public List<User> getUsers() {
		users = userService.getAllUsers();
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getFilteredProfiles() {
		return filteredProfiles;
	}

	public void setFilteredProfiles(List<User> filteredProfiles) {
		this.filteredProfiles = filteredProfiles;
	}
	  
	public List<String> getGroups() {
		groups = Role.getRoles();
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void deleteProfile(){
		System.out.println("delete user" + selectedUser.toString());//delete
		userService.deleteUser(selectedUser);
	}

	

}
