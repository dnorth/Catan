package server.facade;

import server.model.ServerData;
import client.models.ClientModel;

/**
 * The Class GameFacade.
 */
public class GameFacade implements iGameFacade {
	
	private ServerData serverData;

	public GameFacade(ServerData serverData) {
		this.serverData = serverData;
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
