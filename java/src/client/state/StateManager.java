package client.state;

import java.util.Observable;
import java.util.Observer;

import client.facade.Facade;
import client.models.ClientModel;
import client.models.User;
import server.ServerPoller.ServerPoller;

public class StateManager {

	private IStateBase state;
	Facade facade;
	ServerPoller serverPoller;
	
	public StateManager(Facade facade) {
		this.facade = facade;
		this.state = new LoginState(facade);
		this.serverPoller = new ServerPoller(this.facade.getClientCommunicator(), this);
	}

	public void updateStateManager() { // send myPlayerIndex to State
		facade.updatePointersToNewModel();
		facade.setServerPoller(serverPoller);
		System.out.println("STATEMANAGER UPDATED WITH NEW CLIENTMODEL!");
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
}
