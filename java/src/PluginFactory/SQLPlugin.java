package PluginFactory;

import java.util.ArrayList;
import java.util.List;

import SQLDAO.Database;
import SQLDAO.DatabaseException;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import server.model.ServerUser;

public class SQLPlugin extends IPlugin {

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
			db.startTransaction();
			List<ServerUser> serverUsers = db.getUserSQLDAO().getAll();
			db.endTransaction(true);
			return serverUsers;
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ArrayList<IMovesCommand> loadUnexecutedCommands() {
		ArrayList<ServerGame> games = loadGames();
		try {
			db.startTransaction();
			ArrayList<IMovesCommand> commands = new ArrayList<IMovesCommand>();
			for(ServerGame game : games) {
//				System.out.println("MY COMMAND NUMBER: " + String.valueOf(game.getLastCommandSaved()));
				ArrayList<IMovesCommand> newCommands = db.getCommandSQLDAO().getCommandsByGameAfterIndex(game, game.getLastCommandSaved());
				for (IMovesCommand c : newCommands) commands.add(c);
			}
			db.endTransaction(false);
			return commands;
		} catch (DatabaseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<ServerGame> loadGames() {
		try {
			db.startTransaction();
			ArrayList<ServerGame> games = db.getGameSQLDAO().getAll();
			for (ServerGame game : games) {
				ArrayList<Integer> userIDs = db.getGameUserMapSQLDAO().getUserIDsForGame(game.getId());
				for(int userID : userIDs) {
					String color = db.getGameUserMapSQLDAO().getColorForGameAndUser(game.getId(), userID);
					ServerUser user = db.getUserSQLDAO().getById(userID);
					game.addUser(user, color);
				}
			}
			db.endTransaction(false);
//			for (ServerGame game : games) {
//				System.out.println("GAME: " + String.valueOf(game.getId()) + " THIS TIME?: " + String.valueOf(game.getLastCommandSaved()));
//			}
			return games;
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addUserToGame(int userID, int gameID, String color) {
		try {
			db.startTransaction();
			db.getGameUserMapSQLDAO().addPlayerToGame(userID, gameID, color);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveUser(ServerUser user) {
		try {
			db.startTransaction();
			List<ServerUser> allUsers = db.getUserSQLDAO().getAll();
			for (ServerUser u : allUsers) {
				if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
					db.endTransaction(false);
					return;
				}
			}
			db.getUserSQLDAO().add(user);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveGame(ServerGame game) { // updates existing games, adds new ones
		try {
			for (ServerGame g : loadGames()) {
				if (g.getId() == game.getId()) {
					db.startTransaction();
					db.getGameSQLDAO().update(game);
					db.endTransaction(true);
					return;
				}
			}
			db.startTransaction();
			db.getGameSQLDAO().add(game);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveCommand(ServerGame game, IMovesCommand command) {
		//commandDAO.add(game, command);
		try {
			db.startTransaction();
			db.getCommandSQLDAO().add(command);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetPersistence() {
		// TODO Auto-generated method stub
		super.resetPersistence();
	}
}
