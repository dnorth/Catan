package client.turntracker;

import java.util.Observable;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.models.Player;
import client.models.TurnTracker;
import client.state.*;
import client.state.trading.*;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	private StateManager stateManager;
	boolean initialized;
	public TurnTrackerController(ITurnTrackerView view, StateManager stateManager) {
		
		super(view);
		this.stateManager=stateManager;
		this.stateManager.getClientModel().addObserver(this);
		initFromModel();
		initialized = false;
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() 
	{
		//System.out.println("WE MADE IT TO THE ENDTURN FUNCTION!");
		IStateBase state = this.stateManager.getState();
		state.endTurn();
		this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade()));
	}
	
	private void initFromModel() {
		getView().setLocalPlayerColor(CatanColor.WHITE);
	}
	
	public void initializeAllPlayers() {
		GameInfo game = this.stateManager.getFacade().getGame();
		PlayerInfo[] playerList= game.getPlayers().toArray(new PlayerInfo[game.getPlayers().size()]);
		for(PlayerInfo p : playerList) {
			this.getView().initializePlayer(p.getPlayerIndex(), p.getName(), p.getColor());
		}
		initialized = true;
	}
	
	public void updatePlayers() {
		TurnTracker tt = this.stateManager.getClientModel().getTurnTracker();
		int currentTurn = tt.getCurrentTurn();
		int largestArmy = tt.getLargestArmy();
		int longestRoad = tt.getLongestRoad();
		Player[] players = this.stateManager.getClientModel().getPlayers();
		for(Player p : players) {
			int playerIndex = p.getPlayerIndex();
			boolean myTurn = playerIndex == currentTurn;
			boolean myArmy = playerIndex == largestArmy;
			boolean myRoad = playerIndex == longestRoad;
			this.getView().updatePlayer(playerIndex, p.getVictoryPoints(), myTurn, myArmy, myRoad);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (stateManager.isSetTitleColor()) {
			this.getView().setLocalPlayerColor(stateManager.getClientColor());
			stateManager.setSetTitleColor(false);
		}
		
		if (this.stateManager.getState() instanceof GameOverState) {
			stateManager.getFacade().setGame(null);
			stateManager.setState(new JoinGameState(stateManager.getFacade()));
		}
		
		if(this.stateManager.getState() instanceof PlayerWaitingState) {
			this.getView().updateGameState("Waiting For Other Players", false);
			return;
		}
		else if (this.stateManager.getState() instanceof GameOverState ||
				this.stateManager.getState() instanceof JoinGameState ||
				this.stateManager.getState() instanceof LoginState) {
			this.getView().updateGameState("Waiting For Other Players", false);
			return;
		}
		else if (this.stateManager.getState() instanceof RoadBuildingState ||
				this.stateManager.getState() instanceof RobbingState) {
			//System.out.println("ROAD BUILDING STATE");
			return;
		}
		
		if(initialized) {updatePlayers();}
		
		if(!stateManager.getClientModel().newCli()) {
			if (!this.stateManager.clientTurn()) {
				if (!(this.stateManager.getState() instanceof InactivePlayerState) &&
						//!(this.stateManager.getState() instanceof RollingDiceState) &&
						//!(this.stateManager.getState() instanceof OfferingTradeState) &&
						//!(this.stateManager.getState() instanceof TradeOfferedWaitingState) &&
						!(this.stateManager.getState() instanceof ReceivingTradeState)) {
					this.getView().updateGameState("Waiting For Other Players", false);
					this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade()));
				}
			}
			else {
				if (this.stateManager.getClientModel().getTurnTracker().getStatus().equals("FirstRound")) {
					if (!(this.stateManager.getState() instanceof SetupOneActivePlayerState)) {
						this.getView().updateGameState("Place Initial Pieces", false);
						this.stateManager.setState(new SetupOneActivePlayerState(this.stateManager.getFacade()));
						this.initializeAllPlayers();
					}
				}
				else if (this.stateManager.getClientModel().getTurnTracker().getStatus().equals("SecondRound")) {
					if (!(this.stateManager.getState() instanceof SetupTwoActivePlayerState)) {
						this.stateManager.setState(new SetupTwoActivePlayerState(this.stateManager.getFacade()));
					}
				}
				else if (this.stateManager.getClientModel().getTurnTracker().getStatus().equals("Rolling")) {
					if (!(this.stateManager.getState() instanceof RollingDiceState)) {
						//this.updatePlayers(this.stateManager.getClientModel().getPlayers()[this.stateManager.getFacade().getPlayerIndex()], true, false, false);
						this.getView().updateGameState("Roll The Dice", false);
						this.stateManager.setState(new RollingDiceState(this.stateManager.getFacade()));
						this.stateManager.getClientModel().runUpdates();
					}
				}
				else if (!(this.stateManager.getState() instanceof ActivePlayerState) &&
							!(this.stateManager.getState() instanceof TradeOfferedWaitingState)) {
					//System.out.println("MAKE ME THE ACTIVE PLAYER PLEEEEEASE!");
					this.getView().updateGameState("Finish Turn", true);
					this.getView().updateGameState("I'm dooooone", true);
					this.stateManager.setState(new ActivePlayerState(this.stateManager.getFacade()));
					this.stateManager.getClientModel().runUpdates();
				}
			}
		}
		//System.out.println("TURN-TRACKER-CONTROLLER EXIT-STATE: \t" + stateManager.getState().getClass().getSimpleName());
	}
}

