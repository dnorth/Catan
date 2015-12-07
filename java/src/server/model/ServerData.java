package server.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import PluginFactory.IPlugin;
import PluginFactory.JSONPlugin;
import PluginFactory.PluginFactory;
import PluginFactory.SQLPlugin;
import jsonTranslator.JSONToModel;
import server.commands.IMovesCommand;

/**
 * Holds all user, game, and child info for those things.
 */
public class ServerData {
	
	private List<ServerUser> users;
	private List<ServerGame> games;
	private int nextUserID = 12;
	private int nextGameID = 3;
	private int checkpoint = 10;
	private IPlugin plugin;
	
	public ServerData() {
		initServerData();
		setPluginClassSQL();
	}

	public int addUser(String username, String password) {
		for (ServerUser u : users) {
			if (u.getUsername().equals(username)) return -1;
		}
		users.add(new ServerUser(username, password, this.nextUserID));
		plugin.saveUser(new ServerUser(username, password, this.nextUserID));
		this.nextUserID += 1;
		return this.nextUserID-1;
	}

	public ServerUser getUserByID(int id) {
		for (ServerUser u : users) {
			if (u.getPlayerID() == id) return u;
		}
		return null;
	}
	
	public ServerGame getGameByID(int id) {
		for (ServerGame game : games) {
			if (game.getId() == id) return game;
		}
		return null;
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
	
	/**
	 * initServerData
	 * Add standard/default server data as seen from demo server
	 */
	public void initServerData() {
		this.users = new ArrayList<ServerUser>();
		
		this.users.add(new ServerUser("Sam", "sam", 0));
		this.users.add(new ServerUser("Brooke", "brooke", 1));
		this.users.add(new ServerUser("Pete", "pete", 10));
		this.users.add(new ServerUser("Mark", "mark", 11));
		this.users.add(new ServerUser("Quinn", "quinn", -2));
		this.users.add(new ServerUser("Scott", "scott", -3));
		this.users.add(new ServerUser("Hannah", "hannah", -4));
		this.users.add(new ServerUser("Sam", "sam", 0));
		
		this.games = new ArrayList<ServerGame>();
		
		ServerGame initGame0 = new ServerGame("Default Game", 0);
		initGame0.addUser(getUserByID(0), "orange");
		initGame0.addUser(getUserByID(1), "blue");
		initGame0.addUser(getUserByID(10), "red");
		initGame0.addUser(getUserByID(11), "green");
		this.games.add(initGame0);
		
		ServerGame initGame1 = new ServerGame("AI Game", 1);
		initGame1.addUser(getUserByID(10), "orange");
		initGame1.addUser(getUserByID(-2), "blue");
		initGame1.addUser(getUserByID(-3), "puce");
		initGame1.addUser(getUserByID(-4), "white");
		this.games.add(initGame1);
		
		ServerGame initGame2 = new ServerGame("Empty Game", 2);
		initGame2.addUser(getUserByID(0), "orange");
		initGame2.addUser(getUserByID(1), "blue");
		initGame2.addUser(getUserByID(10), "red");
		initGame2.addUser(getUserByID(11), "green");
		this.games.add(initGame2);
		
		
	}

	public int getNextUserID() {
		return nextUserID;
	}


	public int getNextGameID() {
		return nextGameID;
	}
	
	public void incNextUserID() {
		nextUserID++;
	}


	public void incNextGameID() {
		nextGameID++;
	}

	public IPlugin getPlugin() {
		return plugin;
	}

	public void setPlugin(String pluginName) {
		PluginFactory factory = new PluginFactory();
		this.plugin = factory.CreatePlugin(pluginName);
	}
	
	public void setPluginClassJSON() {
		this.plugin = new JSONPlugin();
	}
	
	public void setPluginClassSQL() {
		this.plugin = new SQLPlugin();
	}
	
	public void uploadFromPlugin() {
		this.uploadUsers(plugin.loadUsers());
		this.uploadGames(plugin.loadGames());
		this.executeNewCommands(plugin.loadUnexecutedCommands());
	}
	
	private void uploadUsers (List<ServerUser> newUsers) {
		this.users.addAll(newUsers);
	}
	
	private void uploadGames (List<ServerGame> newGames) {
		this.games.addAll(newGames);
	}
	
	private void executeNewCommands (List<IMovesCommand> commands){
		// iterate through each command
		//		set command's serverGame by ID
		//		execute command
	}
	
	public void setCheckpoint(int cp) {
		this.checkpoint = cp;
	}

	public int getCheckpoint() {
		return checkpoint;
	}

	public void setNextUserID(int nextUserID) {
		this.nextUserID = nextUserID;
	}

	public void setNextGameID(int nextGameID) {
		this.nextGameID = nextGameID;
	}

	public void setPlugin(IPlugin plugin) {
		this.plugin = plugin;
	}
	
	
}
