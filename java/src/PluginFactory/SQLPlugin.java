package PluginFactory;

import java.util.ArrayList;
import java.util.List;

import server.commands.IMovesCommand;
import server.model.ServerData;
import server.model.ServerGame;
import server.model.ServerPlayer;
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
		try {
			return userDAO.getAll();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ArrayList<IMovesCommand> loadUnexecutedCommands() {
		ArrayList<ServerGame> games;
		try {
			games = gameDAO.getAll();
			ArrayList<Integer> indeces = new ArrayList<Integer>();
			for(ServerGame game : games) {
				indeces.add(game.getNumberOfCommands());
			}
			return commandDAO.getCommandsByGameAfterIndex(games, indeces);
		} catch (DatabaseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<ServerGame> loadGames() {
		try {
			ArrayList<ServerGame> games = gameDAO.getAll();
			for (ServerGame game : games) {
				ArrayList<Integer> userIDs = gameUserMapDAO.getPlayerIDsForGame(game.getId());
				for(int userID : userIDs) {
					ServerUser user = userDAO.getById(userID);
					game.addUser(user, user.getColor());
				}
			}
			return games;
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveUser(ServerUser user) {
		try {
			userDAO.add(user);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveGame(ServerGame game) {
		try {
			gameDAO.update(game);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveCommand(ServerGame game, IMovesCommand command) {
		//commandDAO.add(game, command);
		try {
			commandDAO.add(command);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
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
