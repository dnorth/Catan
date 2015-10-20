package client.join;

import java.util.Observable;

import client.base.*;
import client.state.IStateBase;
import client.state.StateManager;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	private StateManager stateManager;
	
	public PlayerWaitingController(IPlayerWaitingView view, StateManager stateManager) {

		super(view);
		this.stateManager = stateManager;
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
		IStateBase state = stateManager.getState();
		String[] AIChoices = state.getFacade().listAI();
		getView().setAIChoices(AIChoices);
		getView().showModal();
	}

	@Override
	public void addAI() {

		IStateBase state = stateManager.getState();
		String AIType = getView().getSelectedAI();
		state.addAI(AIType);
		
		getView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

