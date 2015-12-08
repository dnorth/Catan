package PluginFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import server.commands.IMovesCommand;
import server.model.ServerGame;
import server.model.ServerUser;

import com.google.gson.JsonObject;

public abstract class IPlugin {
	
	public List<ServerUser> loadUsers() {
		return null;
	}
	public List<IMovesCommand> loadUnexecutedCommands() {
		return null;
	}
	public List<ServerGame> loadGames() {
		return null;
	}
	public void addUserToGame(int userID, int gameID, String color) {}
	public void saveUser(ServerUser user) {}
	public void saveGame(ServerGame game) {}
	public void saveCommand(ServerGame game, IMovesCommand command) {}
	public void resetPersistence() {}
}
