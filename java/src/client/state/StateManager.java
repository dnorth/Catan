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
	
	public StateManager(Facade facade) {
		this.facade = facade;
		this.state = new LoginState(facade);
	}

	
	@Override
	public void update(Observable o, Object arg) { // send myPlayerIndex to State
		
	}

	public Facade getFacade() {
		return facade;
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
		facade.setServerPoller(serv);
	}
	
	public ServerPoller getNewServerPoller() {
		return facade.getNewServerPoller();
	}
	
	public ClientModel getClientModel() {
		return facade.getClient();
	}
}
