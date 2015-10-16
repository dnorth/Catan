package client.models;

/**
 * User class holds username and password for login information
 *
 */

public class User {

	private String username;
	private String password;
	private String userCookie;
	private int playerIndex;
	
	public User(String username, String password, String userCookie, int playerIndex) {
		super();
		this.username = username;
		this.password = password;
		this.userCookie = userCookie;
		this.playerIndex = playerIndex;
	}
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
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", userCookie=" + userCookie + ", playerIndex="
				+ playerIndex + "]";
	}
	
	
	
}
