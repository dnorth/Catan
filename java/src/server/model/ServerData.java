package server.model;

import java.util.List;

/**
 * Holds all user, game, and child info for those things.
 */
public class ServerData {
	
	private List<ServerUser> users;
	private List<ServerGame> games;
	
	/**
	 * Get users.
	 * @return list of users
	 */
	public List<ServerUser> getUsers() {
		return users;
	}
	
	/**
	 * Set users.
	 * @param users
	 */
	public void setUsers(List<ServerUser> users) {
		this.users = users;
	}
	
	/**
	 * Get games.
	 * @return list of games
	 */
	public List<ServerGame> getGames() {
		return games;
	}
	
	/**
	 * Set Games.
	 * @param games
	 */
	public void setGames(List<ServerGame>games) {
		this.games = games;
	}

}
