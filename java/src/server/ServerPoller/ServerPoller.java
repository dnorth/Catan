package server.ServerPoller;

import server.proxy.IServer;
import client.models.ClientModel;

/**
 * Polls server periodically to check for changes in model, then receives update <br>
 */

public class ServerPoller {
	
	int currVersion;
	IServer server; //IServer can be real server or mock proxy
	ClientModel client;
	
	/**
	 * Constructs ServerPoller, calls initialize
	 * @param serv pointer to server or mock proxy
	 * @param cli pointer to client model
	 */
	ServerPoller(IServer serv, ClientModel cli){} //calls initialize, gets current version, and sends to server
	
	/**
	 * Begins timer to poll server or proxy
	 */
	void initialize() {} // Begin timer to poll server or proxy
	
	/**
	 * Stops polling server or proxy
	 */
	void stopPolling() {} //Stops polling server or proxy
	
	/**
	 * Compares newest version with currVersion - if different, return true
	 * @param newestVersion new version number
	 * @return true if new version different than currVersion
	 */
	private
	boolean newVersion(int newestVersion){ // compare newest version with currVersion - if different, return new
		return false;
	}
	
	/**
	 * Updates currVersion, receives server's model, and sends to client
	 */
	void getNewVersion() {} // update currVersion, receive server model, and send it to client model
}