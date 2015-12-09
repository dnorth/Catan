package PluginFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import JSONDAO.CommandJSONDAO;
import JSONDAO.GameJSONDAO;
import JSONDAO.UserJSONDAO;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import server.model.ServerPlayer;
import server.model.ServerUser;

public class JSONPlugin extends IPlugin {

	private GameJSONDAO gameDAO;
	private UserJSONDAO userDAO;
	private CommandJSONDAO commandDAO;
	
	
	
	
	public JSONPlugin() {
		try {
			this.gameDAO = new GameJSONDAO();
			this.userDAO = new UserJSONDAO();
			this.commandDAO = new CommandJSONDAO();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ServerUser> loadUsers() {
		List<ServerUser> serverUsers = null;
		
		try {
			serverUsers = userDAO.getAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return serverUsers;
	}

	@Override
	public List<IMovesCommand> loadUnexecutedCommands() {
		try {
			List<ServerGame> games = gameDAO.getAll();
			ArrayList<IMovesCommand> commands = new ArrayList<IMovesCommand>();
			for(ServerGame game : games) {
				List<IMovesCommand> newCommands = commandDAO.getCommandsByGameAfterIndex(game);
				for (IMovesCommand c : newCommands) commands.add(c);
			}
			return commands;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ServerGame> loadGames() {
		try {
			List<ServerGame> games = gameDAO.getAll();
			
			//System.out.println("Load games:");
			//for(ServerGame game : games) {
			//	System.out.println(game.toString());
			//}
			return games;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void addUserToGame(int userID, int gameID, String color) {
		try {
			ServerUser su = userDAO.getUserByID(userID);
			ServerGame sg = gameDAO.getGameByID(gameID);
			if (su != null && sg != null) {
				sg.addUser(su, color);
				gameDAO.update(sg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveUser(ServerUser user) {
		try {
			userDAO.add(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveGame(ServerGame game) {
		try {
			gameDAO.update(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveCommand(ServerGame game, IMovesCommand command) {
		try {
			commandDAO.add(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetPersistence() {
		File dir = new File("jsonFiles");
		if (dir.exists() && dir.isDirectory()) {
			for (File file : dir.listFiles()){
				file.delete();
			}
		}
	}

	public GameJSONDAO getGameDAO() {
		return gameDAO;
	}

	public void setGameDAO(GameJSONDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	public UserJSONDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserJSONDAO userDAO) {
		this.userDAO = userDAO;
	}

	public CommandJSONDAO getCommandDAO() {
		return commandDAO;
	}

	public void setCommandDAO(CommandJSONDAO commandDAO) {
		this.commandDAO = commandDAO;
	}
	
}
