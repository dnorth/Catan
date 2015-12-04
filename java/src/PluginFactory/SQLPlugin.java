package PluginFactory;

import java.util.List;

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
	public List<ServerUser> loadUsers() {
		return userDAO.getAll();
	}
	
	@Override
	public List<IMovesCommand> loadUnexecutedCommands() {
		return commandDAO.getCommandsByGameAfterIndex(gameIDs, indeces);
	}

	@Override
	public List<ServerGame> loadGames() {
		return gameDAO.getAll();
	}

	@Override
	public void saveUser(ServerUser user) {
		userDAO.add(user);
	}

	@Override
	public void saveGame(ServerGame game) {
		gameDAO.update(game);
	}

	@Override
	public void saveCommand(ServerGame game, IMovesCommand command) {
		commandDAO.add(game, command);
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
