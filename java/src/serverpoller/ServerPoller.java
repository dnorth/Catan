package serverpoller;

/**
 * Polls server periodically to check for changes in model, then receives update <br>
 * 
 *
 */
public class ServerPoller {
	
	int currVersion; //use as an int, or seperate class?
	//IServer server
	//Client client
	
	ServerPoller(/*IServer, Client*/){} //calls initialize, gets current version, and sends to server
	
	void initialize() {} // Begin timer to call server or proxy
	
	private
	boolean newVersion(int newestVersion){ // compare newest version with currVersion - if different, return new
		return false;
	}
	
	/*statemodeldoc*/ void getNewVersion() {} // update currVersion, receive server model, and return it
	
	void sendVersion (/*statemodeldoc*/){} // sends model to server
}
