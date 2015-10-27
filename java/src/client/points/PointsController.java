package client.points;

import java.util.Observable;

import client.base.*;
import client.exceptions.OutOfBoundsException;
import client.models.Player;
import client.state.GameOverState;
import client.state.JoinGameState;
import client.state.StateManager;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;
	private StateManager stateManager;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView, StateManager sm) {
		
		super(view);
		setFinishedView(finishedView);
		this.stateManager = sm;
		this.stateManager.addObserver(this);
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		try {
			getPointsView().setPoints(getLocalVictoryPoints());
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	private int getLocalVictoryPoints() throws OutOfBoundsException{
		Player[] players = this.stateManager.getClientModel().getPlayers();
		for(Player player : players) {
			if(player.getPlayerID() == stateManager.getFacade().getLocalPlayer().getId()) {
				return player.getVictoryPoints();
			}
		}
		//This should never happen.....
		throw new OutOfBoundsException("Could not find local player with id: " + stateManager.getFacade().getLocalPlayer().getId());
	}

	@Override
	public void update(Observable o, Object arg) {
		if(!stateManager.getClientModel().newCli()) {
			for (Player p : stateManager.getClientModel().getPlayers()) {
				if (p.getVictoryPoints() > 9) {
					System.out.println("SET GAMEOVER STATE TRUE2");
					finishedView.setWinner(p.getName(), p.getPlayerIndex() == stateManager.getFacade().getPlayerIndex());
					finishedView.showModal();
					stateManager.setState(new GameOverState(stateManager.getFacade()));
					stateManager.getFacade().setGame(null);
				}
			}
			initFromModel();
		}
	}
	
}

