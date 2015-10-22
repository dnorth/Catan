package client.state;

import java.util.Observable;
import java.util.Observer;

import client.facade.Facade;
import client.models.ClientModel;
import client.models.User;
import server.ServerPoller.ServerPoller;

public class StateManager implements Observer {

	private IStateBase state;
	Facade facade;
	ServerPoller serverPoller;
	
	public StateManager(Facade facade) {
		this.facade = facade;
		this.state = new LoginState(facade);
		this.serverPoller = new ServerPoller(this.facade.getClientCommunicator(), this);
	}

	
	@Override
	public void update(Observable o, Object arg) { // send myPlayerIndex to State
		
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
		return this.facade.getClient();
	}
	
	public void activatePoller() {
		this.serverPoller.setActive(true);
	}
	
	public void deactivatePoller() {
		this.serverPoller.setActive(false);
	}
	
	public int getCurrentVersion() {
		return this.getClientModel().getVersion();
	}
	
	public void addObserver(Observer o) {
		this.facade.addObserver(o);
	}
}
