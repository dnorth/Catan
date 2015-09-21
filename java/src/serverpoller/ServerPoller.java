package serverpoller;

import server.proxy.IServer;
import client.models.ClientModel;

/**
 * Polls server periodically to check for changes in model, then receives update <br>
 * 
 *
 */
public class ServerPoller {
	
	int currVersion;
	IServer server; //IServer can be real server or mock proxy
	ClientModel client;
	
	ServerPoller(/*IServer, Client*/){} //calls initialize, gets current version, and sends to server
	
	void initialize() {} // Begin timer to poll server or proxy
	
	void stopPolling() {} //Stops polling server or proxy
	
	private
	boolean newVersion(int newestVersion){ // compare newest version with currVersion - if different, return new
		return false;
	}
	
	void getNewVersion() {} // update currVersion, receive server model, and send it to client model
	
	void sendVersion (/*statemodeldoc*/){} // sends model to server
}
