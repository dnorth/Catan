package server.ServerPoller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.JsonObject;

import client.data.GameInfo;
import client.models.Player;
import client.state.JoinGameState;
import client.state.LoginState;
import client.state.StateManager;
import jsonTranslator.JSONToModel;
import jsonTranslator.ModelToJSON;
import server.proxy.IProxy;
import shared.definitions.CatanColor;

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
	private boolean forceUpdate;
	private int currTurn;
	private ArrayList<CatanColor> playerColors;
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
		currTurn = -1;
		forceUpdate = false;
		playerColors = new ArrayList<CatanColor>();
		initialize();
	}
	
	public void forcePollServer() { //I feel like we need a function like this, but I can't seem to get it to work.
		this.forceUpdate = true;
	}
	
	private class PollEvent extends TimerTask {		
		public void run() {
			try {
//				System.out.println("CURRENT STATE: " + stateManager.getState().getClass().getName());
				if(stateManager.getState() instanceof JoinGameState) {
					setFacadeGameList();
				}
				else if(!(stateManager.getState() instanceof LoginState)) {
					updateCurrentModel(server.getGameModel(stateManager.getFacade().getUserAndGameCookie())); //cookies?
				}
			} catch (NullPointerException e) {
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
		//System.out.println("CLIENT MODEL: " + cookies.toString());
		CatanColor color = null;
		try {
			color = jsonToModelTranslator.getMyColor(cookies, this.stateManager.getFacade().getPlayerIndex());
		} catch (Exception e) {
			System.out.println("Couldn't find color.");
		}
		//System.out.println("SERVER PROXY TURN: " + String.valueOf(JSONToModel.translateTurnTracker(cookies).getCurrentTurn()));
		boolean isNewVersion = newVersion(JSONToModel.translateVersion(cookies),
				JSONToModel.translateNumberOfPlayers(cookies),
				JSONToModel.translateTurnTracker(cookies).getCurrentTurn(),
				JSONToModel.translatePlayers(cookies),
				color);
		
		if(isNewVersion) {
			//System.out.println("NEW MODEL: " + cookies.toString());
			this.stateManager.getClientModel().update(jsonToModelTranslator.translateClientModel(cookies));
			this.stateManager.updateStateManager();
			this.stateManager.getClientModel().runUpdates();
			if (this.forceUpdate) this.forceUpdate = false; 
			/*try{
				FileWriter out = new FileWriter("C:\\Users\\Dallin\\Desktop\\JSONcomps\\Theirs.txt");
				out.write(cookies.toString());
				out.close();
			}
			catch (Exception e){}
			try{
				ModelToJSON mj = new ModelToJSON();
				FileWriter out = new FileWriter("C:\\Users\\Dallin\\Desktop\\JSONcomps\\Mine.txt");
				out.write(mj.translateModel(this.stateManager.getClientModel()).toString());
				out.close();
			}
			catch (Exception e){}*/
		}
	}
	/**
	 * Compares newest version with currVersion - if different, return true
	 * @param newestVersion new version number
	 * @return true if new version different than currVersion
	 */
	private boolean newVersion(int newestVersion, int numPlayers, int turn, Player[] players, CatanColor color){ // compare newest version with currVersion - if different, return new
		boolean newnew = false;
		if (newestVersion != this.stateManager.getCurrentVersion()) {
			newnew = true;
		}
		if (numPlayers > currNumPlayers) {
			currNumPlayers = numPlayers;
			if(numPlayers > 1) newnew = true;
		}
		if (color != currColor) {
			currColor = color;
			newnew = true;
		}
		if (turn != currTurn) {
			//System.out.println("NEW BEACUSE NEW TURN");
			currTurn = turn;
			newnew = true;
		}
		return newnew;
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

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
}
