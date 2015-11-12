package server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all user, game, and child info for those things.
 */
public class ServerData {
	
	private List<ServerUser> users;
	private List<ServerGame> games;
	private int nextUserID = 12;
	
	public ServerData() {
		this.users = new ArrayList<ServerUser>();
		this.games = new ArrayList<ServerGame>();
	}

	public int addUser(String username, String password) {
		for (ServerUser u : users) {
			if (u.getUsername().equals(username)) return -1;
		}
		users.add(new ServerUser(username, password, this.nextUserID));
		this.nextUserID += 1;
		return this.nextUserID-1;
	}
	
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

	public int getPlayerID(String username, String password) {
		for (ServerUser u : users) {
			if (u.getUsername().equals(username)) {
				if (u.getPassword().equals(password)) return u.getPlayerID();
				else return -1;
			}
		}
		return -1;
	}

}
