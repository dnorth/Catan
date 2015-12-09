package server.model;

import java.util.ArrayList;
import java.util.List;

import PluginFactory.IPlugin;
import PluginFactory.JSONPlugin;
import PluginFactory.PluginFactory;
import PluginFactory.SQLPlugin;
import server.commands.IMovesCommand;
import server.exceptions.AlreadyPlayedDevCardException;
import server.exceptions.CantBuildThereException;
import server.exceptions.DontHaveDevCardException;
import server.exceptions.GameFullException;
import server.exceptions.InsufficientResourcesException;
import server.exceptions.InvalidMaritimeTradeException;
import server.exceptions.InvalidPlayerException;
import server.exceptions.InvalidPlayerIndexException;
import server.exceptions.InvalidRollException;
import server.exceptions.InvalidStatusException;
import server.exceptions.NoTradeOfferedException;
import server.exceptions.NotYourTurnException;
import server.exceptions.OutOfPiecesException;
import server.exceptions.RobberIsAlreadyThereException;

/**
 * Holds all user, game, and child info for those things.
 */
public class ServerData {
	
	private List<ServerUser> users;
	private List<ServerGame> games;
	private int nextUserID = 1;
	private int nextGameID = 3;
	private int checkpoint = 10;
	private IPlugin plugin;
	
	public ServerData() {
	}
	public void init() {
		this.users = plugin.loadUsers();
		this.games = plugin.loadGames();
		List<IMovesCommand> commandsToExecute = plugin.loadUnexecutedCommands();
		executeCommandList(commandsToExecute);
		if (games.size() == 0) {
			initServerData();
		}
	}
	
	public void executeCommandList(List<IMovesCommand> commands) {
		System.out.println("\nNUMBER OF UNEXECUTED COMMANDS: " + String.valueOf(commands.size()) + "\n");
		for (IMovesCommand c : commands) {
			try {
				c.setGame(getGameByID(c.getGameID()));
				c.execute();
				addCommandToGame(getGameByID(c.getGameID()), c);
			} catch (InvalidStatusException | InsufficientResourcesException
					| CantBuildThereException | NotYourTurnException
					| OutOfPiecesException | NoTradeOfferedException
					| InvalidPlayerException | InvalidMaritimeTradeException
					| RobberIsAlreadyThereException | InvalidRollException
					| DontHaveDevCardException | AlreadyPlayedDevCardException
					| InvalidPlayerIndexException e) {
				System.out.println("ERROR executing unexecuted command.");
				e.printStackTrace();
			}
		}
	}

	public int addUser(String username, String password) {
		for (ServerUser u : users) {
			if (u.getUsername().equals(username)) return -1;
		}
		ServerUser user = new ServerUser(username, password, this.nextUserID);
		users.add(user);
		plugin.saveUser(user);
		return this.nextUserID++;
	}
	
	public int addGame(ServerGame game) {
		for (ServerGame g : games) {
			if (g.getId() == game.getId()) return -1;
		}
		games.add(game);
		plugin.saveGame(game);
		return 0;
	}
	
	public void addUserToGame(int gameID, int playerID, String color) throws GameFullException {
		ServerGame game = getGameByID(gameID);
		if (game.hasPlayerID(playerID)) {
			game.changeUserColor(getUserByID(playerID), color);
		}
		else if (game.getPlayers().size() > 3) {
			throw new GameFullException();
		}
		else {
			game.addUser(getUserByID(playerID), color);
		}
		plugin.addUserToGame(playerID, gameID, color);
	}
	
	public void addCommandToGame(ServerGame game, IMovesCommand command) {
		game.addCommand(command);
		plugin.saveCommand(game, command);
		if ((command.getCommandNumber() % checkpoint) == 0) {
			System.out.println("SAVING GAME!!!");
			game.setLastCommandSaved(command.getCommandNumber());
			plugin.saveGame(game);
		}
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
		
		addUser("Sam", "sam");
		addUser("Brooke", "brooke");
		addUser("Pete", "pete");
		addUser("Mark", "mark");
		addUser("Quinn", "quinn");
		addUser("Scott", "scott");
		addUser("Hannah", "hannah");
		addUser("Sam", "sam");
		
		this.games = new ArrayList<ServerGame>();
		
		try {
			ServerGame initGame0 = new ServerGame("Default Game", 0);
			addGame(initGame0);
			addUserToGame(0, getPlayerID("Sam", "sam"), "orange");
			addUserToGame(0, getPlayerID("Brooke", "brooke"), "blue");
			addUserToGame(0, getPlayerID("Pete", "pete"), "red");
			addUserToGame(0, getPlayerID("Mark", "mark"), "green");
			
			ServerGame initGame1 = new ServerGame("AI Game", 1);
			addGame(initGame1);
			addUserToGame(1, getPlayerID("Pete", "pete"), "orange");
			addUserToGame(1, getPlayerID("Quinn", "quinn"), "blue");
			addUserToGame(1, getPlayerID("Scott", "scott"), "puce");
			addUserToGame(1, getPlayerID("Hannah", "hannah"), "white");
			
			ServerGame initGame2 = new ServerGame("Empty Game", 2);
			addGame(initGame2);
			addUserToGame(2, getPlayerID("Sam", "sam"), "orange");
			addUserToGame(2, getPlayerID("Brooke", "brooke"), "blue");
			addUserToGame(2, getPlayerID("Pete", "pete"), "red");
			addUserToGame(2, getPlayerID("Mark", "mark"), "green");
		} catch (GameFullException e) {
			System.out.println("ERROR WITH INITIALIZING ServerData");
			e.printStackTrace();
		}		
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
		System.out.println("SET PLUGIN: " + pluginName);
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
