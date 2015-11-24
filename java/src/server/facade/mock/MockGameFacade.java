package server.facade.mock;

import client.models.ClientModel;
import server.facade.iGameFacade;

public class MockGameFacade implements iGameFacade{

	@Override
	public ClientModel getGameModel(int gameID) {
		return new ClientModel();
	}

}
