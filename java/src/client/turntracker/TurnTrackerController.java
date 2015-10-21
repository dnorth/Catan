package client.turntracker;

import java.util.Observable;

import shared.definitions.CatanColor;
import client.base.*;
import client.state.StateManager;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	private StateManager stateManager;
	public TurnTrackerController(ITurnTrackerView view, StateManager stateManager) {
		
		super(view);
		this.stateManager=stateManager;
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() 
	{
		stateManager.getState().endTurn();
	}
	
	private void initFromModel() {
		//<temp>
		getView().setLocalPlayerColor(CatanColor.RED);
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

