package client.facade;

import server.proxy.ClientCommunicator;
import client.models.ClientModel;

/**
 * Facade class interfaces from GUI to Client Communicator
 */
public class Facade {
	private PlayerManager playerManager;
	private TurnManager turnManager;
	private ClientModel client;
	private ClientCommunicator clientCommunicator;
	
	public Facade (ClientModel cli) {
		
	}
}
