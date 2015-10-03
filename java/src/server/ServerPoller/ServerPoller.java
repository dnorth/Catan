package server.ServerPoller;

import java.util.Timer;
import java.util.TimerTask;

import jsonTranslator.JSONToModel;
import server.proxy.IProxy;
import client.models.ClientModel;

import com.google.gson.JsonObject;

/**
 * Polls server periodically to check for changes in model, then receives update <br>
 */
public class ServerPoller {
	
	private int currVersion;
	private IProxy server; //IServer can be real server or mock proxy
	private ClientModel client;
	private JSONToModel jsonToModelTranslator;
	private Timer timer;
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
	
	private class PollEvent extends TimerTask {
		public void run() {
			updateCurrentModel(server.getGameModel(null)); //cookies?
		}
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
		timer = new Timer();
		//set timer to poll server every second
		timer.schedule(new PollEvent(), 0, 1000);
	} // Begin timer to poll server or proxy

	/**
	 * Stops polling server or proxy
	 */
	void stopPolling() {
		timer.cancel();
		timer.purge();
	} //Stops polling server or proxy
	
	
	/**
	 * Updates currVersion, gets new model, and replaces old model
	 * @param cookies
	 */
	public void updateCurrentModel(JsonObject cookies) {
		JsonObject serverModel = server.getGameModel(cookies);
		
		if(newVersion(JSONToModel.translateVersion(cookies))) {
			client = jsonToModelTranslator.translateClientModel(serverModel);
			//TODO reconnect managers/facade to model
		}
	}
	/**
	 * Compares newest version with currVersion - if different, return true
	 * @param newestVersion new version number
	 * @return true if new version different than currVersion
	 */
	private boolean newVersion(int newestVersion){ // compare newest version with currVersion - if different, return new
		if (newestVersion != currVersion) {
			currVersion = newestVersion;
			return true;
		}
		else
			return false;
	}

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