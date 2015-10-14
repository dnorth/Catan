package client.state;

import java.util.Observable;
import java.util.Observer;

public class State implements Observer {

	private IStateBase currentState;

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
}
