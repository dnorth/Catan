package server.ServerPoller;

import java.util.Timer;
import java.util.TimerTask;

import jsonTranslator.JSONToModel;
import server.proxy.IProxy;
import shared.definitions.CatanColor;
import client.data.GameInfo;
import client.facade.Facade;
import client.state.JoinGameState;
import client.state.PlayerWaitingState;
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
	private int currNumPlayers;
	private CatanColor currColor;
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
			try {
				if(stateManager.getState() instanceof JoinGameState) {
					System.out.println("Poller printing game list");
					setFacadeGameList();
				} else if (stateManager.getState() instanceof PlayerWaitingState) {
					System.out.println("Poller printing one game");
					updateCurrentModel(server.getGameModel(stateManager.getFacade().getUserAndGameCookie())); //cookies?
				} else {
					System.out.println("Poller in Idle State");
				}
				/*if (gameID != -1) {
					GameInfo game = new GameInfo();
					
					for(GameInfo g: gamesList) {
						if (g.getId() == gameID) {
							game = g;
						}
					}
					
					stateManager.getFacade().setGame(game);
				}*/
			} catch (NullPointerException e) {
				System.out.println("SERVER POLLER NULL EXCEPTION:");
				e.printStackTrace();
			}
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
		System.out.println("CLIENT MODEL: " + cookies.toString());
		CatanColor color = null;
		try {
			color = jsonToModelTranslator.getMyColor(cookies, this.stateManager.getFacade().getPlayerIndex());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean isNewVersion = newVersion(JSONToModel.translateVersion(cookies), JSONToModel.translateNumberOfPlayers(cookies), color);
		if(isNewVersion) {
//			System.out.println("NEW MODEL: " + cookies.toString());
			this.stateManager.getClientModel().update(jsonToModelTranslator.translateClientModel(cookies));
			this.stateManager.updateStateManager();
			this.stateManager.getClientModel().runUpdates();
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
	private boolean newVersion(int newestVersion, int numPlayers, CatanColor color){ // compare newest version with currVersion - if different, return new
		if (newestVersion != this.stateManager.getCurrentVersion()) {
			return true;
		}
		else if (numPlayers > currNumPlayers) {
			currNumPlayers = numPlayers;
			return true;
		}
		else if (color != currColor) {
			currColor = color;
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
	
	public int getGameID() {
		return stateManager.getFacade().getGame().getId();
	}
	
	public void setFacadeGameList() {
		GameInfo[] newGames = stateManager.getFacade().getGamesList();
		GameInfo[] oldGames = stateManager.getFacade().getGames();
		boolean matches = gameInfoMatches(newGames, oldGames);
		if(!matches) {
			System.out.println("Updating Games List.");
			stateManager.getFacade().setGames(newGames);
			this.stateManager.getClientModel().runUpdates();
		}
	}
	
	public boolean gameInfoMatches(GameInfo[] newGames, GameInfo[] oldGames) {
		int i=0;
		boolean matches = true;
		if(oldGames == null) {
			matches = false;
		} else if( newGames.length != oldGames.length) {
			matches = false;
		} else {
			for(GameInfo g : newGames) {
				if( !g.equals(oldGames[i])) {
					matches = false;
					break;
				}
				i++;
			}
		}
		return matches;
	}
}
