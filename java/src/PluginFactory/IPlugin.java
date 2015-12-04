package PluginFactory;

import server.commands.IMovesCommand;
import server.model.ServerGame;
import server.model.ServerUser;

import com.google.gson.JsonObject;

public abstract class IPlugin {
	
	public JsonObject loadUsers() {
		return null;
	}
	public JsonObject loadGames() {
		return null;
	}
	public void saveUsers(ServerUser[] users) {}
	public void saveGame(ServerGame game) {}
	public void saveCommand(ServerGame game, IMovesCommand command) {}
	
}
