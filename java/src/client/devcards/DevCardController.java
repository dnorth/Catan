package client.devcards;

import java.util.Observable;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;
import client.state.RoadBuildingState;
import client.state.RobbingState;
import client.state.StateManager;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	private StateManager stateManager;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction, StateManager stateManager) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		this.stateManager = stateManager;
		this.stateManager.addObserver(this);
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		stateManager.getFacade().buyCard();
		getBuyCardView().closeModal();
	}
	
	public int getOldAmount(DevCardType type) {
		return stateManager.getClientModel().getPlayers()[stateManager
				.getFacade().getPlayerIndex()].getOldDevCards()
				.getSpecifiedDevCardCount(type);
	}
	
	public int getNewAmount(DevCardType type) {
		return stateManager.getClientModel().getPlayers()[stateManager
				.getFacade().getPlayerIndex()].getNewDevCards()
				.getSpecifiedDevCardCount(type);
	}
	
	public int getTotalAmount(DevCardType type) {
		return getOldAmount(type) + getNewAmount(type);
	}
	
	public boolean enoughRoads() {
		return stateManager.getClientModel().getPlayers()[stateManager.getFacade().getPlayerIndex()].getRoads() > 1;
	}
	
	public boolean canPlayCard(DevCardType type) {
		switch(type) {
		case SOLDIER:
			return (!stateManager.hasPlayedDevCard() && getOldAmount(type) > 0);
		case YEAR_OF_PLENTY:
			return (!stateManager.hasPlayedDevCard() && getOldAmount(type) > 0);
		case MONOPOLY:
			return (!stateManager.hasPlayedDevCard() && getOldAmount(type) > 0);
		case ROAD_BUILD:
			return (!stateManager.hasPlayedDevCard() && getOldAmount(type) > 0 && enoughRoads());
		case MONUMENT:
			return (getTotalAmount(type) > 0);
		}
		return false;
	}

	@Override
	public void startPlayCard() {
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		stateManager.getFacade().playMonopolyCard(resource);
		stateManager.setPlayedDevCard(true);
		stateManager.getClientModel().runUpdates();
	}

	@Override
	public void playMonumentCard() {
		stateManager.getFacade().playMonumentCard();
	}

	@Override
	public void playRoadBuildCard() {
		stateManager.setState(new RoadBuildingState(stateManager.getFacade()));
		stateManager.setPlayedDevCard(true);
		stateManager.getClientModel().runUpdates();
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		stateManager.setState(new RobbingState(stateManager.getFacade()));
		stateManager.setPlayedDevCard(true);
		stateManager.getClientModel().runUpdates();
//		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		stateManager.getFacade().playYearOfPlentyCard(resource1, resource2);
		stateManager.setPlayedDevCard(true);
		stateManager.getClientModel().runUpdates();
	}

	@Override
	public void update(Observable o, Object arg) {
		for (DevCardType type : DevCardType.values()) {
			getPlayCardView().setCardEnabled(type, canPlayCard(type));
			getPlayCardView().setCardAmount(type, getTotalAmount(type));
		}
	}

}

