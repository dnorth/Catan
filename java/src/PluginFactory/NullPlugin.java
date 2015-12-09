package PluginFactory;

import java.util.ArrayList;
import java.util.List;

import server.commands.IMovesCommand;
import server.model.ServerGame;
import server.model.ServerUser;

public class NullPlugin extends IPlugin {

	@Override
	public List<ServerUser> loadUsers() {
		return new ArrayList<ServerUser>();
	}

	@Override
	public List<IMovesCommand> loadUnexecutedCommands() {
		return new ArrayList<IMovesCommand>();
	}

	@Override
	public List<ServerGame> loadGames() {
		return new ArrayList<ServerGame>();
	}

	@Override
	public void addUserToGame(int userID, int gameID, String color) {
	}

	@Override
	public void saveUser(ServerUser user) {
	}

	@Override
	public void saveGame(ServerGame game) {
	}

	@Override
	public void saveCommand(ServerGame game, IMovesCommand command) {
	}

	@Override
	public void resetPersistence() {
	}

}
