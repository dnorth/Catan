package server.ServerPoller;

import java.util.Timer;
import java.util.TimerTask;

import jsonTranslator.JSONToModel;
import server.proxy.IProxy;
import client.facade.Facade;
import client.data.GameInfo;
import client.models.ClientModel;
import client.state.StateManager;

import com.google.gson.JsonObject;

/**
 * Polls server periodically to check for changes in model, then receives update <br>
 */
public class ServerPoller {
	
	private IProxy server; //IServer can be real server or mock proxy
	private StateManager stateManager;
	private JSONToModel jsonToModelTranslator;
	private Timer timer;
	private boolean active = false;
	private int currNumPlayers;
	private int gameID;
	/**
	 * Constructs ServerPoller, calls initialize
	 * @param serv pointer to server or mock proxy
	 * @param cli pointer to client model
	 */
	public ServerPoller(IProxy server, StateManager stateManager){
		this.server = server;
		this.stateManager = stateManager;
		jsonToModelTranslator = new JSONToModel();
		currNumPlayers = 0;
		initialize();
	}
	
	public void forcePollServer() { //I feel like we need a function like this, but I can't seem to get it to work.
		Facade f = stateManager.getFacade();
		JsonObject cookies = f.getUserAndGameCookie();
		JsonObject cm = server.getGameModel(cookies);
		this.updateCurrentModel(cm);
	}
	
	private class PollEvent extends TimerTask {		
		public void run() {
			if (active) try {
				System.out.println("POLLING");
				updateCurrentModel(server.getGameModel(stateManager.getFacade().getUserAndGameCookie())); //cookies?
				if (gameID != -1) {
					GameInfo game = new GameInfo();
					GameInfo[] gamesList = stateManager.getFacade().getGamesList();
					for(GameInfo g: gamesList) {
						if (g.getId() == gameID) {
							game = g;
						}
					}
					
					stateManager.getFacade().setGame(game);
				}
			} catch (NullPointerException e) {
				System.out.println("SERVER POLLER NULL EXCEPTION:");
				e.printStackTrace();
			}
			else System.out.println("NOT YET RUNNING POLLER");
		}
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
		
		boolean isNewVersion = newVersion(JSONToModel.translateVersion(cookies), JSONToModel.translateNumberOfPlayers(cookies));
		System.out.println("NEW VERSION = " + isNewVersion);
		if(isNewVersion) {
			System.out.println("UPDATING CURRENT MODEL");
//			System.out.println("NEW MODEL: " + cookies.toString());
			this.stateManager.getClientModel().update(jsonToModelTranslator.translateClientModel(cookies));
			/* THIS WAS MY VALLIANT ATTEMPT TO GET THE ADDAI METHOD TO WORK
			 * The pointers are all messed up in the facade and that makes this next to impossible
			 * I wish I had seen this "TODO" a couple hours ago
			 * ClientModel toEditFacade = this.stateManager.getClientModel();
			 * this.stateManager.getFacade().getGame().setPlayers(toEditFacade.getPlayers());
			 */
			
			
			//TODO reconnect managers/facade to model
		}
	}
	/**
	 * Compares newest version with currVersion - if different, return true
	 * @param newestVersion new version number
	 * @return true if new version different than currVersion
	 */
	private boolean newVersion(int newestVersion, int numPlayers){ // compare newest version with currVersion - if different, return new
		System.out.println("NEW NUMBER OF PLAYERS = " + numPlayers + " -- CURRENT NUMBER OF PLAYERS = " + currNumPlayers);
		if (newestVersion != this.stateManager.getCurrentVersion()) {
			return true;
		}
		else if(numPlayers > currNumPlayers) {
			currNumPlayers = numPlayers;
			return true;
		}
		else
			return false;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

}
