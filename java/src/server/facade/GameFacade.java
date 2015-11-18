package server.facade;

import server.Server;
import server.model.ServerData;
import client.models.ClientModel;

/**
 * The Class GameFacade.
 */
public class GameFacade implements iGameFacade {
	
	private ServerData serverData;

	public GameFacade() {
		this.serverData = Server.getServerData();
	}

	/**
	 *  Facade for the command game/model
	 */
	@Override
	public ClientModel getGameModel(int gameID) {
		return serverData.getGameByID(gameID).getClientModel();
	}

	public ServerData getServerData() {
		return serverData;
	}
	
}
