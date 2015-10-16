package client.state;

import java.util.Observable;
import java.util.Observer;

import client.facade.Facade;
import client.models.ClientModel;
import client.models.User;

public class StateManager implements Observer {

	private IStateBase state;
	public User user;
	Facade facade;
	
	public StateManager(Facade facade) {
		this.facade = facade;
		this.state = new LoginState(facade);
		this.user = null;
	}

	
	@Override
	public void update(Observable o, Object arg) { // send myPlayerIndex to State
		
	}


	public int getMyPlayerIndex() {
		return user.getPlayerIndex();
	}


	public void setMyPlayerIndex(int myPlayerIndex) {
		this.user.setPlayerIndex(myPlayerIndex);
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
