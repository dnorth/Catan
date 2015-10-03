package server.ServerPoller;

import server.proxy.IProxy;

import com.google.gson.JsonObject;

import client.models.ClientModel;
import jsonTranslator.JSONToModel;

/**
 * Polls server periodically to check for changes in model, then receives update <br>
 */
public class ServerPoller {
	
	private int currVersion;
	private IProxy server; //IServer can be real server or mock proxy
	private ClientModel client;
	private JSONToModel jsonToModelTranslator;
	/**
	 * Constructs ServerPoller, calls initialize
	 * @param serv pointer to server or mock proxy
	 * @param cli pointer to client model
	 */
	public ServerPoller(IProxy serv, ClientModel cli){
		server = serv;
		client = cli;
		jsonToModelTranslator = new JSONToModel();
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
	
	
	public void updateCurrentModel(JsonObject cookies) {
		JsonObject serverModel = server.getGameModel(cookies);
		
		//TODO VERIFY VERSION NUMBER CHANGE
		
		client = jsonToModelTranslator.translateClientModel(serverModel);
	}
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

	public int getCurrVersion() {
		return currVersion;
	}

	public void setCurrVersion(int currVersion) {
		this.currVersion = currVersion;
	}

	public JSONToModel getJsonToModelTranslator() {
		return jsonToModelTranslator;
	}

	public void setJsonToModelTranslator(JSONToModel jsonToModelTranslator) {
		this.jsonToModelTranslator = jsonToModelTranslator;
	}

	public IProxy getServer() {
		return server;
	}

	public ClientModel getClient() {
		return client;
	}
}