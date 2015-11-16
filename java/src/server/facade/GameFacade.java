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
	public ClientModel getGameModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
