package client.join;

import java.util.Observable;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
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

	/**
	 * make a list of players from the current game
	 * getView().setPlayers(that list)
	 * getView().setAIChoices(if you happen to hve AI)
	 * showModal
	 */
	@Override
	public void start() {
		System.out.println("PLAYER WAITING");
		IStateBase state = stateManager.getState();
		GameInfo game = stateManager.getFacade().getGame();
		System.out.println(game.toString());
		PlayerInfo[] playerList= game.getPlayers().toArray(new PlayerInfo[game.getPlayers().size()]);
		
		for(PlayerInfo p : playerList) {
			System.out.println("Player Name: " + p.getName());
		}
		
		getView().setPlayers(playerList);
		String[] AIChoices = state.getFacade().listAI();
		getView().setAIChoices(AIChoices);
		getView().showModal();
	}

	/**
	 * Do whatever you need to do to generate an AI and and it to the player list;
	 */
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

