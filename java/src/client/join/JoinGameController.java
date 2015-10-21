package client.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import client.models.User;
import client.state.IStateBase;
import client.state.StateManager;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private StateManager stateManager;
	private GameInfo[] games;
	private PlayerInfo localPlayer;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView, StateManager stateManager) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		this.stateManager = stateManager;
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	/**
	 * get list of games from server, save them into your pre-game model
	 * JoinGameView().setGames(list of games, your player info)
	 * showModal
	 */
	@Override
	public void start() {
		IStateBase state = stateManager.getState();
		games = state.getFacade().getGamesList();
		localPlayer = state.getFacade().getUser();
		getJoinGameView().setGames(games, localPlayer);
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}

	/**
	 * Create a new Game board based on the options in the View(Random or not)
	 * Send create game request to server
	 * update Game List
	 * closeModal
	 */
	@Override
	public void createNewGame() {
		
		IStateBase state = stateManager.getState();
		
		String  title          = getNewGameView().getTitle();
		boolean useRandomHexes   = getNewGameView().getRandomlyPlaceHexes();
		boolean useRandomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean useRandomPorts   = getNewGameView().getUseRandomPorts();
		System.out.println("Trying to create new game with:   Name: " + title + ";   Random Tiles: " + useRandomHexes +
				 ";   Random Numbers: " + useRandomNumbers + ";   Random Ports: " + useRandomPorts);
		boolean gameCreated = state.createNewGame(title, useRandomHexes, useRandomNumbers, useRandomPorts);
		//stateManager.getState().		create new game using data.
		System.out.println("Game Created: " + gameCreated);
		if(gameCreated) {
			getNewGameView().closeModal();
			games = state.getFacade().getGamesList();
			getJoinGameView().setGames(games, localPlayer);
		}
	}

	/**
	 * Iterate through player in GameInfo and 
	 * disable each color that has already been used in ColorSelectView
	 * check if you are already in
	 * if so,call JoinGame with the color you had already picked
	 */
	@Override
	public void startJoinGame(GameInfo game) {

		System.out.println("\nIN STARTJOINGAME FUNCTION!");
		System.out.print("\n" + game.toString());
		IStateBase state = stateManager.getState();
		int myPlayerId = state.getFacade().getUser().getId();
		List<PlayerInfo> players = game.getPlayers();
		for(int playerCounter = 0; playerCounter < players.size(); playerCounter++) {
			CatanColor cc = players.get(playerCounter).getColor();
			if(players.get(playerCounter).getId() == myPlayerId) {
				state.startJoinGame(game);
				joinGame(cc);
			}
			else {
				getSelectColorView().setColorEnabled(cc, false);
			}
		}
		state.startJoinGame(game);
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
	
		for(CatanColor cc : CatanColor.values()) {
			getSelectColorView().setColorEnabled(cc, true);
		}
		getJoinGameView().closeModal();
	}

	/**
	 * call join game on server
	 */
	@Override
	public void joinGame(CatanColor color) {
		System.out.println("\nINSIDE JOINGAME FUNCTION");
		IStateBase state = stateManager.getState();
		boolean canJoinGame = state.canJoinGame();
		if(canJoinGame) {
			//CatanColor cc = getSelectColorView().getSelectedColor(); //is this unnecessary because of the parameter to this function??
			state.joinGame(color);
			System.out.println("WHY IS THE FREAKING COLOR VIEW SHOWING FOR RE-JOIN?! 1");
			if(getSelectColorView().isModalShowing()) {
				getSelectColorView().closeModal();
				System.out.println("WHY IS THE FREAKING COLOR VIEW SHOWING FOR RE-JOIN?! 2");
			}
			getJoinGameView().closeModal();
			System.out.println("WHY IS THE FREAKING COLOR VIEW SHOWING FOR RE-JOIN?! 3");
			joinAction.execute();
			if(getSelectColorView().isModalShowing()) {
				getSelectColorView().closeModal();
				System.out.println("WHY IS THE FREAKING COLOR VIEW SHOWING FOR RE-JOIN?! 4");
			}
			System.out.println("WHY IS THE FREAKING COLOR VIEW SHOWING FOR RE-JOIN?! 5");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

