package PluginFactory;

import server.commands.IMovesCommand;
import server.model.ServerData;
import server.model.ServerGame;
import server.model.ServerUser;
import SQLDAO.*;

import com.google.gson.JsonObject;

public class SQLPlugin extends IPlugin {

	private ServerData serverData;
	private GameSQLDAO gameDAO;
	private UserSQLDAO userDAO;
	private CommandSQLDAO commandDAO;
	private GameUserMapSQLDAO gameUserMapDAO;
	
	@Override
	public JsonObject loadUsers() {
		// TODO Auto-generated method stub
		return super.loadUsers();
	}

	@Override
	public JsonObject loadGames() {
		// TODO Auto-generated method stub
		return super.loadGames();
	}

	@Override
	public void saveUsers(ServerUser[] users) {
		// TODO Auto-generated method stub
		super.saveUsers(users);
	}

	@Override
	public void saveGame(ServerGame game) {
		// TODO Auto-generated method stub
		super.saveGame(game);
	}

	@Override
	public void saveCommand(ServerGame game, IMovesCommand command) {
		// TODO Auto-generated method stub
		super.saveCommand(game, command);
	}

	public ServerData getServerData() {
		return serverData;
	}

	public void setServerData(ServerData serverData) {
		this.serverData = serverData;
	}

	public GameSQLDAO getGameDAO() {
		return gameDAO;
	}

	public void setGameDAO(GameSQLDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	public UserSQLDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserSQLDAO userDAO) {
		this.userDAO = userDAO;
	}

	public CommandSQLDAO getCommandDAO() {
		return commandDAO;
	}

	public void setCommandDAO(CommandSQLDAO commandDAO) {
		this.commandDAO = commandDAO;
	}

	public GameUserMapSQLDAO getGameUserMapDAO() {
		return gameUserMapDAO;
	}

	public void setGameUserMapDAO(GameUserMapSQLDAO gameUserMapDAO) {
		this.gameUserMapDAO = gameUserMapDAO;
	}

}
