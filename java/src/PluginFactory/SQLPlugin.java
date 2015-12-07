package PluginFactory;

import java.util.ArrayList;
import java.util.List;

import server.commands.IMovesCommand;
import server.model.ServerData;
import server.model.ServerGame;
import server.model.ServerUser;
import SQLDAO.*;

public class SQLPlugin extends IPlugin {

	private ServerData serverData;
	private Database db;
	
	public SQLPlugin() {
		this.db = new Database();
		try {
			Database.initialize();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<ServerUser> loadUsers() {
		
		try {
			return db.getUserSQLDAO().getAll();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ArrayList<IMovesCommand> loadUnexecutedCommands() {
		ArrayList<ServerGame> games;
		try {
			games = db.getGameSQLDAO().getAll();
			ArrayList<Integer> indeces = new ArrayList<Integer>();
			for(ServerGame game : games) {
				indeces.add(game.getNumberOfCommands());
			}
			return db.getCommandSQLDAO().getCommandsByGameAfterIndex(games, indeces);
		} catch (DatabaseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<ServerGame> loadGames() {
		try {
			ArrayList<ServerGame> games = db.getGameSQLDAO().getAll();
			for (ServerGame game : games) {
				ArrayList<Integer> userIDs = db.getGameUserMapSQLDAO().getUserIDsForGame(game.getId());
				for(int userID : userIDs) {
					String color = db.getGameUserMapSQLDAO().getColorForGameAndUser(game.getId(), userID);
					ServerUser user = db.getUserSQLDAO().getById(userID);
					game.addUser(user, color);
				}
			}
			return games;
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addUserToGame(int userID, int gameID) {
		// TODO Auto-generated method stub
//		super.addUserToGame(userID, gameID);
	}

	@Override
	public void saveUser(ServerUser user) {
		try {
			db.getUserSQLDAO().add(user);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveGame(ServerGame game) {
		try {
			db.getGameSQLDAO().update(game);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveCommand(ServerGame game, IMovesCommand command) {
		//commandDAO.add(game, command);
		try {
			db.getCommandSQLDAO().add(command);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetPersistence() {
		// TODO Auto-generated method stub
		super.resetPersistence();
	}

	public ServerData getServerData() {
		return serverData;
	}

	public void setServerData(ServerData serverData) {
		this.serverData = serverData;
	}
}
