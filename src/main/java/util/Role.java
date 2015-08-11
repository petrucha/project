package util;

import java.util.ArrayList;

public class Role {

	public static final String ADMINISTRATOR = "Administrator";
	public static final String USER = "User";

	public static ArrayList<String> getRoles() {
		ArrayList<String> roles = new ArrayList<String>();
		roles.add(ADMINISTRATOR);
		roles.add(USER);
		return roles;
	}
}
