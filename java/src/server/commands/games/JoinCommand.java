package server.commands.games;

import server.commands.IGamesCommand;
import server.exceptions.GameFullException;
import server.model.ServerData;
import server.model.ServerGame;

/**
 * The Class JoinCommand.
 */
public class JoinCommand implements IGamesCommand {

	ServerData serverData;
	int gameID;
	int playerID;
	String color;
	
	public JoinCommand(ServerData serverData, int pID, int gID, String color) {
		this.serverData = serverData;
		this.playerID = pID;
		this.gameID = gID;
		this.color = color;
	}

	/**
	 * Joins a game.
	 */
	@Override
	public void execute() throws GameFullException {
		serverData.addUserToGame(gameID, playerID, color);
		serverData.getGameByID(gameID).getClientModel().increaseVersion();
	}

}
