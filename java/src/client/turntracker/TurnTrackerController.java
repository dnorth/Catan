package client.turntracker;

import java.util.Observable;

import shared.definitions.CatanColor;
import client.base.*;
import client.state.ActivePlayerState;
import client.state.InactivePlayerState;
import client.state.SetupOneActivePlayerState;
import client.state.SetupTwoActivePlayerState;
import client.state.StateManager;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	private StateManager stateManager;
	public TurnTrackerController(ITurnTrackerView view, StateManager stateManager) {
		
		super(view);
		this.stateManager=stateManager;
		this.stateManager.getClientModel().addObserver(this);
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
		if (!this.stateManager.clientTurn()) {
			if (!(this.stateManager.getState() instanceof InactivePlayerState)) {
				this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade()));
			}
		}
		else {
			if (this.stateManager.getClientModel().getTurnTracker().getStatus() == "FirstRound") {
				if (!(this.stateManager.getState() instanceof SetupOneActivePlayerState)) {
					this.stateManager.setState(new SetupOneActivePlayerState(this.stateManager.getFacade()));
				}
			}
			else if (this.stateManager.getClientModel().getTurnTracker().getStatus() == "SecondRound") {
				if (!(this.stateManager.getState() instanceof SetupTwoActivePlayerState)) {
					this.stateManager.setState(new SetupTwoActivePlayerState(this.stateManager.getFacade()));
				}
			}
			else if (!(this.stateManager.getState() instanceof ActivePlayerState)) {
				this.stateManager.setState(new ActivePlayerState(this.stateManager.getFacade()));
			}
		}
	}

}

