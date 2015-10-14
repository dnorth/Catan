package client.state;

import java.util.Observable;
import java.util.Observer;

import client.facade.Facade;
import client.models.ClientModel;

public class StateManager implements Observer {

	private IStateBase currentState;
	Facade facade;
	
	public StateManager(Facade facade) {
		this.facade = facade;
		this.currentState = new LoginState(facade);
	}

	int myPlayerIndex;
	
	@Override
	public void update(Observable o, Object arg) { // send myPlayerIndex to State
		
	}
	
}
