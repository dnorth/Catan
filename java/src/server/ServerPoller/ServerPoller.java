package server.ServerPoller;

import server.proxy.IProxy;
import client.models.ClientModel;

/**
 * Polls server periodically to check for changes in model, then receives update <br>
 */
public class ServerPoller {
	
	int currVersion;
	IProxy server; //IServer can be real server or mock proxy
	ClientModel client;
	/**
	 * Constructs ServerPoller, calls initialize
	 * @param serv pointer to server or mock proxy
	 * @param cli pointer to client model
	 */
	public ServerPoller(IProxy serv, ClientModel cli){
		server = serv;
		client = cli;
		initialize();
	}
	
	/**
	 * Set client
	 * @param cli pointer to client model
	 */
	public void setClient(ClientModel cli) {
		client = cli;
	}
	
	/**
	 * Set server or mock proxy
	 * @param serv pointer to server or mock proxy
	 */
	public void setServer(IProxy serv) {
		server = serv;
	}
	
	/**
	 * Begins timer to poll server or proxy
	 */
	void initialize() {
		
	} // Begin timer to poll server or proxy

	/**
	 * Stops polling server or proxy
	 */
	void stopPolling() {
		
	} //Stops polling server or proxy
	
	/**
	 * Compares newest version with currVersion - if different, return true
	 * @param newestVersion new version number
	 * @return true if new version different than currVersion
	 */
	private boolean newVersion(int newestVersion){ // compare newest version with currVersion - if different, return new
		return false;
	}

	/**
	 * Updates currVersion, receives server's model, and sends to client
	 */
	void getNewVersion() {
		
	} // update currVersion, receive server model, and send it to client model
}