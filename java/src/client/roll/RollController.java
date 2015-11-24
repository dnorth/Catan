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
	public void setRollShowing(boolean showing){
		this.stateManager.setRollShowing(showing);
	}
	
	@Override
	public void rollDice() {
		if(this.getRollView().isModalShowing()) {
			this.getRollView().closeModal();
		}
		int rollNumber = stateManager.getFacade().rollDice();
		getResultView().setRollValue(rollNumber);
		getResultView().showModal();
		stateManager.setState(new InactivePlayerState(stateManager.getFacade()));
		timer.cancel();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (this.stateManager.getState() instanceof RollingDiceState) {
			stateManager.setPlayedDevCard(false);
			stateManager.setRollShowing(true);
			this.getRollView().showModal();
			timer = new Timer();
			timer.schedule(new AutomaticRoll(), 5000);
		}
	}
	
	private class AutomaticRoll extends TimerTask
	{

		@Override
		public void run() {
			rollDice();
		}
		
	}

}

