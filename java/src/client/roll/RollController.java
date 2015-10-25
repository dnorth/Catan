package client.roll;

import java.util.Observable;

import client.base.*;
import client.state.RollingDiceState;
import client.state.StateManager;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;
	private StateManager stateManager;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView, StateManager stateManager) {
		super(view);
		this.stateManager=stateManager;
		this.stateManager.getClientModel().addObserver(this);
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
//		getResultView().setRollValue(stateManager.getFacade().rollDice());
		getResultView().showModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (stateManager.getState() instanceof RollingDiceState) {
			rollDice();
		}
	}

}

