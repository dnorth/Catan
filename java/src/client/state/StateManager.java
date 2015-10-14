package client.state;

import java.util.Observable;
import java.util.Observer;

import client.facade.Facade;
import client.models.ClientModel;

public class StateManager implements Observer {

	private IStateBase currentState;
	public int myPlayerIndex;
	Facade facade;
	
	public StateManager(Facade facade) {
		this.facade = facade;
		this.currentState = new LoginState(facade);
		//TODO???
		this.myPlayerIndex = -1;
	}

	
	@Override
	public void update(Observable o, Object arg) { // send myPlayerIndex to State
		
	}


	public IStateBase getCurrentState() {
		return currentState;
	}


	public void setCurrentState(IStateBase currentState) {
		this.currentState = currentState;
	}


	public int getMyPlayerIndex() {
		return myPlayerIndex;
	}


	public void setMyPlayerIndex(int myPlayerIndex) {
		this.myPlayerIndex = myPlayerIndex;
	}


	public Facade getFacade() {
		return facade;
	}


	public void setFacade(Facade facade) {
		this.facade = facade;
	}
	
}
