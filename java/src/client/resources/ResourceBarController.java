package client.resources;

import java.util.*;

import client.base.*;
import client.models.DevCards;
import client.models.Player;
import client.models.Resources;
import client.state.ActivePlayerState;
import client.state.IStateBase;
import client.state.JoinGameState;
import client.state.StateManager;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	StateManager stateManager;
	public ResourceBarController(IResourceBarView view, StateManager sm) {
		super(view);
		stateManager = sm;
		this.stateManager.addObserver(this);
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	
	/**
	 * For each resourceType, buildingType, and cardType for your client's player
	 * call getView().setElementAmount
	 */
	public void setResources()
		{
		System.out.println("MY PLAYER INDEX: " + String.valueOf(this.stateManager.getFacade().getPlayerIndex()));
		if (this.stateManager.getFacade().getPlayerIndex() == -1) return;
		Player p = stateManager.getClientModel().getPlayers()[this.stateManager.getFacade().getPlayerIndex()];
		Resources r = p.getResources();
		DevCards d = p.getNewDevCards(); 
		System.out.println();
		getView().setElementAmount(ResourceBarElement.WOOD, r.getWoodCount());
		getView().setElementAmount(ResourceBarElement.BRICK, r.getBrickCount());
		getView().setElementAmount(ResourceBarElement.SHEEP, r.getSheepCount());
		getView().setElementAmount(ResourceBarElement.WHEAT, r.getWheatCount());
		getView().setElementAmount(ResourceBarElement.ORE, r.getOreCount());
		
		getView().setElementAmount(ResourceBarElement.ROAD, p.getRoads());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, p.getSettlements());
		getView().setElementAmount(ResourceBarElement.CITY, p.getCitiesNum());
		getView().setElementAmount(ResourceBarElement.SOLDIERS, p.getSoldiers());
		getView().setElementAmount(ResourceBarElement.PLAY_CARD, d.getTotalCount());
		if (stateManager.getFacade().canBuyCard()) getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		else getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}
	
	
	/**
	 * Verify they can build a road (resources and remaining settlement amount)
	 * if so: call the 	executeElementAction(ResourceBarElement.ROAD);
	 * else
	 * display message why they can't
	 * 
	 */
	@Override
	public void buildRoad() {
		stateManager.getState().buildRoad();
		executeElementAction(ResourceBarElement.ROAD);
	}

	
	/** 
	 * same as for build road
	 */
	@Override
	public void buildSettlement() {
		stateManager.getState().buildSettlement();
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	/** 
	 * same as for build road
	 */
	@Override
	public void buildCity() {
		stateManager.getState().buildCity();
		executeElementAction(ResourceBarElement.CITY);
	}
	
	

	@Override
	public void buyCard() {
		stateManager.getState().buyCard();
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	
	/** 
	 * same as for build road
	 */
	@Override
	public void playCard() {
		stateManager.getState().playCard();
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}
	
	public boolean devCardsLeft() {
		return stateManager.getClientModel().getDeck().getTotalCount() > 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (this.stateManager.getState() instanceof JoinGameState) return;
		this.setResources();
		IStateBase state = this.stateManager.getState();
		if(state instanceof ActivePlayerState) {
			int playerIndex = this.stateManager.getFacade().getPlayerIndex();
			if(this.stateManager.getFacade().getCanDo().canBuyRoad(playerIndex)) {
				this.getView().setElementEnabled(ResourceBarElement.ROAD, true);
			}
			else {
				this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
			}
			if(this.stateManager.getFacade().getCanDo().canBuySettlement(playerIndex)) {
				this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
			}
			else {
				this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			}
			if(this.stateManager.getFacade().getCanDo().canUpgradeSettlement(playerIndex)) {
				this.getView().setElementEnabled(ResourceBarElement.CITY, true);
			}
			else {
				this.getView().setElementEnabled(ResourceBarElement.CITY, false);
			}
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, devCardsLeft() && stateManager.getFacade().canBuyCard());
			this.getView().setElementEnabled(ResourceBarElement.SOLDIERS, true);
			this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
			
		}
		else {
			this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
			this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			this.getView().setElementEnabled(ResourceBarElement.CITY, false);
			this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
			this.getView().setElementEnabled(ResourceBarElement.SOLDIERS, false);
			

		}
	}

}

