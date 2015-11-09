package client.state;

import java.util.Observer;

import client.facade.Facade;
import client.models.ClientModel;
import client.roll.RollController;
import server.ServerPoller.ServerPoller;
import shared.definitions.CatanColor;

public class StateManager {

	private IStateBase state;
	Facade facade;
	ServerPoller serverPoller;
	//RollController roller;
	boolean placing;
	boolean currentlyRobbing;
	boolean setTitleColor;
	boolean playedDevCard;
	boolean showingRollModal;
	
	public StateManager(Facade facade) {
		this.facade = facade;
		this.state = new LoginState(facade);
		this.serverPoller = new ServerPoller(this.facade.getClientCommunicator(), this);
		this.placing = false;
		this.currentlyRobbing = false;
		this.setTitleColor = false;
		this.playedDevCard = false;
		this.showingRollModal = false;
	}
	
	//public void setRollController(RollController rc){
	//	this.roller = rc;
	//}
	
	public void setRollShowing(boolean showing) {
		this.showingRollModal = showing;
	}
	
	public boolean rollResultShowing(){
		return this.showingRollModal;
		//return roller.resultShowing();
	}

	public void updateStateManager() { // send myPlayerIndex to State
		facade.updatePointersToNewModel();
		facade.setServerPoller(serverPoller);
		//System.out.println("STATEMANAGER UPDATED WITH NEW CLIENTMODEL!");
	}

	public Facade getFacade() {
		return this.facade;
	}


	public void setFacade(Facade facade) {
		this.facade = facade;
	}


	public IStateBase getState() {
		return state;
	}


	public void setState(IStateBase state) {
		this.state = state;
	}
	
	public void setServerPoller(ServerPoller serv) {
		this.facade.setServerPoller(serv);
	}
	
	public ClientModel getClientModel() {
		return this.facade.getModel();
	}
	
	public int getCurrentVersion() {
		return this.getClientModel().getVersion();
	}
	
	public int getCurrentNumberOfPlayers() {
		return this.getClientModel().getPlayers().length;
	}
	
	public void addObserver(Observer o) {
		this.facade.addObserver(o);
	}
	
	public boolean clientTurn() {
		return (this.getClientModel().getTurnTracker().getCurrentTurn() == this.facade.getPlayerIndex());
	}

	public boolean isPlacing() {
		return placing;
	}

	public void setPlacing(boolean placing) {
		this.placing = placing;
	}
	
	public void setCurrentlyRobbing(boolean currentRob) {
		this.currentlyRobbing = currentRob;
	}
	
	public boolean getCurrentlyRobbing() {
		return this.currentlyRobbing;
	}
	
	public CatanColor getClientColor() {
		return this.facade.getLocalPlayer().getColor();
	}

	public boolean isSetTitleColor() {
		return setTitleColor;
	}

	public void setSetTitleColor(boolean setTitleColor) {
		this.setTitleColor = setTitleColor;
	}

	public boolean hasPlayedDevCard() {
		return playedDevCard;
	}

	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}
}
