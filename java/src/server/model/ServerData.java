package server.model;

public class ServerData {
	
	private ServerUser[] users;
	private ServerGame[] games;
	
	/**
	 * Get users.
	 * @return list of users
	 */
	public ServerUser[] getUsers() {
		return users;
	}
	
	/**
	 * Set users.
	 * @param users
	 */
	public void setUsers(ServerUser[] users) {
		this.users = users;
	}
	
	/**
	 * Get games.
	 * @return list of games
	 */
	public ServerGame[] getGames() {
		return games;
	}
	
	/**
	 * Set Games.
	 * @param games
	 */
	public void setGames(ServerGame[] games) {
		this.games = games;
	}

}
