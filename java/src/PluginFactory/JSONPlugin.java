package PluginFactory;

import java.io.FileNotFoundException;
import java.util.List;

import server.commands.IMovesCommand;
import server.model.ServerData;
import server.model.ServerGame;
import server.model.ServerUser;
import JSONDAO.*;
import jsonTranslator.ModelToJSON;

import com.google.gson.JsonObject;

public class JSONPlugin extends IPlugin {

	private ServerData serverData;
	private GameJSONDAO gameDAO;
	private UserJSONDAO userDAO;
	private CommandJSONDAO commandDAO;
	private ModelJSONDAO modelDAO;
	

	
	
	@Override
	public List<ServerUser> loadUsers() {
		List<ServerUser> serverUsers = null;
		
		try {
			serverUsers = userDAO.getAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serverUsers;
	}

	@Override
	public List<IMovesCommand> loadUnexecutedCommands() {
		// TODO Auto-generated method stub
		return super.loadUnexecutedCommands();
	}

	@Override
	public List<ServerGame> loadGames() {
		// TODO Auto-generated method stub
		return super.loadGames();
	}
	
	@Override
	public void addUserToGame(int userID, int gameID) {
		// TODO Auto-generated method stub
		super.addUserToGame(userID, gameID);
	}

	@Override
	public void saveUser(ServerUser user) {
		userDAO.add(user);
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
