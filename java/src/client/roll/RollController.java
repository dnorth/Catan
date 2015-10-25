package client.roll;

import java.util.Observable;

import client.base.*;
import client.state.*;


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
		
		this.getRollView().closeModal();
		int rollNumber = stateManager.getFacade().rollDice();
		getResultView().setRollValue(rollNumber);
		getResultView().showModal();
		stateManager.setState(new InactivePlayerState(stateManager.getFacade()));
	}

	@Override
	public void update(Observable o, Object arg) {
		//System.out.println("ROLL-CONTROLLER ENTRANCE-STATE: \t\t" + stateManager.getState().getClass().getSimpleName());
		if (this.stateManager.getState() instanceof RollingDiceState) {
			System.out.println("HOORAY WE GOT TO THE ROLLING DICE STATE!");
			this.getRollView().showModal();
		}
		//System.out.println("ROLL-CONTROLLER EXIT-STATE: \t\t" + stateManager.getState().getClass().getSimpleName());
	}

}

