package PluginFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import JSONDAO.CommandJSONDAO;
import JSONDAO.GameJSONDAO;
import JSONDAO.ModelJSONDAO;
import JSONDAO.UserJSONDAO;
import server.commands.IMovesCommand;
import server.model.ServerGame;
import server.model.ServerUser;

public class JSONPlugin extends IPlugin {

	private GameJSONDAO gameDAO;
	private UserJSONDAO userDAO;
	private CommandJSONDAO commandDAO;
	private ModelJSONDAO modelDAO;
	
	
	
	
	public JSONPlugin() {
		try {
			this.gameDAO = new GameJSONDAO();
			this.userDAO = new UserJSONDAO();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.commandDAO = new CommandJSONDAO();
		this.modelDAO = new ModelJSONDAO();
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
		List<ServerGame> games;
		try {
			games = gameDAO.getAll();
			ArrayList<Integer> indeces = new ArrayList<Integer>();
			for(ServerGame game : games) {
				indeces.add(game.getNumberOfCommands());
			}
			return commandDAO.getCommandsByGameAfterIndex(games, indeces);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ServerGame> loadGames() {
		try {
			return gameDAO.getAll();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void addUserToGame(int userID, int gameID, String color) {
		try {
			ServerUser su = userDAO.getUserByID(userID);
			
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
		commandDAO.add(command);
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

	public ModelJSONDAO getModelDAO() {
		return modelDAO;
	}

	public void setModelDAO(ModelJSONDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	
	
}
