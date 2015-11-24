package client.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.models.ClientModel;
import client.state.IStateBase;
import client.state.InactivePlayerState;
import client.state.JoinGameState;
import client.state.PlayerWaitingState;
import client.state.SetupOneActivePlayerState;
import client.state.StateManager;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	private StateManager stateManager;
	
	public PlayerWaitingController(IPlayerWaitingView view, StateManager stateManager) {

		super(view);
		this.stateManager = stateManager;
		this.stateManager.addObserver(this);
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
		IStateBase state = stateManager.getState();
		GameInfo game = stateManager.getFacade().getGame();
		PlayerInfo[] playerList= game.getPlayers().toArray(new PlayerInfo[game.getPlayers().size()]);
		
		getView().setPlayers(playerList);
		if (playerList.length != 4) {
			try{
				String[] AIChoices = state.getFacade().listAI();
				getView().setAIChoices(AIChoices);
			}
			catch (Exception e){}
		}
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
		}

	@Override
	public void update(Observable o, Object arg) {
		if(getView().isModalShowing()) {			
			if (this.stateManager.getState() instanceof PlayerWaitingState) {
				getView().closeModal();
				GameInfo game = stateManager.getFacade().getGame();
				PlayerInfo[] playerList= game.getPlayers().toArray(new PlayerInfo[game.getPlayers().size()]);
				String userCookie = stateManager.getFacade().getLocalPlayer().getUserCookie();
				getView().setPlayers(playerList);
				getView().showModal();
				
				//Set the local player so that he has the real player index
				for(PlayerInfo p : playerList) {
					if(p.getId() == stateManager.getFacade().getLocalPlayer().getId()) {
						p.setUserCookie(userCookie);
						stateManager.getFacade().setLocalPlayer(p);
						break;
					}
				}
				
				if(playerList.length == 4) {
					if (this.stateManager.getState() instanceof PlayerWaitingState) {
						stateManager.setState(new InactivePlayerState(this.stateManager.getFacade()));
					}
					getView().closeModal();
				}
			}
		}
	}

}

