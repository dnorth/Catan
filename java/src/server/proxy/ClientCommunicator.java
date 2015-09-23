package server.proxy;

import client.models.ClientModel;

public class ClientCommunicator {

	private GameProxy gameProxy;
	private GamesProxy gamesProxy;
	private MovesProxy movesProxy;
	private UtilProxy utilProxy;
	private UserProxy userProxy;
	private IServer server;
	private ClientModel clientModel;
	
	/**
	 * @param serv
	 * @param model
	 */
	ClientCommunicator(IServer serv, ClientModel model){
		server = serv;
		clientModel = model;
		initialize();
	}
	
	/**
	 * 
	 */
	private void initialize() {
		gameProxy = new GameProxy();
		gamesProxy = new GamesProxy();
		movesProxy = new MovesProxy();
		utilProxy = new UtilProxy();
		userProxy = new UserProxy();
	}
	
	/**
	 * @param newModel
	 */
	public void setClient(ClientModel newModel) {
		clientModel = newModel;
	}
	
}
