package client.models;

/**
 * User class holds username and password for login information
 *
 */

public class User {

	private String username;
	private String password;
	private String userCookie;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserCookie() {
		return userCookie;
	}
	public void setUserCookie(String userCookie) {
		this.userCookie = userCookie;
	}
}
