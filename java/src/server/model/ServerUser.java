package server.model;

/**
 * Contains server-level user info.
 */
public class ServerUser {
	
	private String username;
	private String password;
	private int playerID;
	
	public ServerUser(String username, String password, int playerID) {
		this.username = username;
		this.password = password;
		this.playerID = playerID;
	}
	/**
	 * Get username.
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Set username.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Get password.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Set password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

}
