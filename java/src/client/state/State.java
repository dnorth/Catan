package client.state;

import java.util.Observable;
import java.util.Observer;

import client.models.ClientModel;

public class State implements Observer {

	private IStateBase currentState;
	ClientModel model;
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	
}
