package server.facade;

import server.commands.games.JoinCommand;
import server.model.ServerData;
import client.data.GameInfo;


/**
 * The Class GamesFacade.
 */
public class GamesFacade implements iGamesFacade {

	
	ServerData serverData;
	
	
	
	public GamesFacade(ServerData serverData) {
		super();
		this.serverData = serverData;
	}

	/**
	 * Facade for the command games/list
	 */
	@Override
	public GameInfo[] listGames() {

		return null;
	}

	/**
	 * Facade for the command games/create
	 */
	@Override
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
		return null;
	}

	/**
	 * Facade for the command games/join
	 */
	@Override
	public String joinGame(int id, String color) {
		
		JoinCommand command = new JoinCommand(serverData, id, color);
		command.execute();
		return null;
		
	}

}
