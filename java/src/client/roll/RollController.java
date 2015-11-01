package client.roll;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import client.base.*;
import client.state.*;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;
	private StateManager stateManager;
	Timer timer;

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
		timer = new Timer();
		stateManager.setRollController(this);
	}
	
	public boolean resultShowing(){
		return resultView.isModalShowing();
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
		if(this.getRollView().isModalShowing()) {
			this.getRollView().closeModal();
		}
		else {
			System.out.println("ROLL CONTROLLER SHOULD NEVER GET HERE. LINE 47");
		}
		int rollNumber = stateManager.getFacade().rollDice();
		getResultView().setRollValue(rollNumber);
		getResultView().showModal();
		stateManager.setState(new InactivePlayerState(stateManager.getFacade()));
		timer.cancel();
	}

	@Override
	public void update(Observable o, Object arg) {
		//System.out.println("ROLL-CONTROLLER");
		//System.out.println("\tROLL-VIEW: " + this.getRollView().isModalShowing());
		//System.out.println("\tRESULT-VIEW: " + this.getResultView().isModalShowing());
		//System.out.println("ROLL-CONTROLLER ENTRANCE-STATE: \t\t" + stateManager.getState().getClass().getSimpleName());
		if (this.stateManager.getState() instanceof RollingDiceState) {
			//System.out.println("HOORAY WE GOT TO THE ROLLING DICE STATE!");
			stateManager.setPlayedDevCard(false);
			this.getRollView().showModal();
			//set timer to roll in 5 seconds
			timer = new Timer();
			timer.schedule(new AutomaticRoll(), 5000, 5000);
		}
		//System.out.println("ROLL-CONTROLLER EXIT-STATE: \t\t" + stateManager.getState().getClass().getSimpleName());
	}
	
	private class AutomaticRoll extends TimerTask
	{

		@Override
		public void run() {
			rollDice();
		}
		
	}

}

