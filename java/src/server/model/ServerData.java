package server.model;

public class ServerData {
	
	private User[] users;
	private Game[] games;
	
	/**
	 * Get users.
	 * @return list of users
	 */
	public User[] getUsers() {
		return users;
	}
	
	/**
	 * Set users.
	 * @param users
	 */
	public void setUsers(User[] users) {
		this.users = users;
	}
	
	/**
	 * Get games.
	 * @return list of games
	 */
	public Game[] getGames() {
		return games;
	}
	
	/**
	 * Set Games.
	 * @param games
	 */
	public void setGames(Game[] games) {
		this.games = games;
	}

}
