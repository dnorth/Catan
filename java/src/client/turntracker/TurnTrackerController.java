package client.turntracker;

import java.util.Observable;

import shared.definitions.CatanColor;
import client.base.*;
import client.state.*;
import client.state.trading.*;


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
		getView().setLocalPlayerColor(CatanColor.RED);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (this.stateManager.getState() instanceof GameOverState ||
				this.stateManager.getState() instanceof JoinGameState ||
				this.stateManager.getState() instanceof LoginState ||
				this.stateManager.getState() instanceof PlayerWaitingState) return;
		if(!stateManager.getClientModel().newCli()) {
			if (!this.stateManager.clientTurn()) {
				if (!(this.stateManager.getState() instanceof InactivePlayerState) &&
						//!(this.stateManager.getState() instanceof RollingDiceState) &&
						//!(this.stateManager.getState() instanceof OfferingTradeState) &&
						//!(this.stateManager.getState() instanceof TradeOfferedWaitingState) &&
						!(this.stateManager.getState() instanceof ReceivingTradeState)) {
					this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade()));
				}
			}
			else {
				if (this.stateManager.getClientModel().getTurnTracker().getStatus().equals("FirstRound")) {
					if (!(this.stateManager.getState() instanceof SetupOneActivePlayerState)) {
						this.stateManager.setState(new SetupOneActivePlayerState(this.stateManager.getFacade()));
					}
				}
				else if (this.stateManager.getClientModel().getTurnTracker().getStatus().equals("SecondRound")) {
					if (!(this.stateManager.getState() instanceof SetupTwoActivePlayerState)) {
						this.stateManager.setState(new SetupTwoActivePlayerState(this.stateManager.getFacade()));
					}
				}
//<<<<<<< Updated upstream
				else if (this.stateManager.getClientModel().getTurnTracker().getStatus().equals("Rolling")) {
					if (!(this.stateManager.getState() instanceof RollingDiceState)) {
						this.stateManager.setState(new RollingDiceState());
					}
				}
				else if (!(this.stateManager.getState() instanceof ActivePlayerState)) {
				/*else if (!(this.stateManager.getState() instanceof ActivePlayerState) &&
							!(this.stateManager.getState() instanceof TradeOfferedWaitingState) &&
							!(this.stateManager.getState() instanceof RollingDiceState)) {
>>>>>>> Stashed changes*/
					this.stateManager.setState(new ActivePlayerState(this.stateManager.getFacade()));
				}
			}
		}
	}

}

