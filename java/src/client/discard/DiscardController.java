package client.discard;

import java.util.Observable;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import client.models.Player;
import client.models.Resources;
import client.state.StateManager;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	private StateManager stateManager;
	private Resources toDiscard;
	private Player p;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView, StateManager stateManager) {
		
		super(view);
		
		this.waitView = waitView;
		this.stateManager = stateManager;
		this.stateManager.addObserver(this);
		initialize();
	}
	
	private void initialize() {
		toDiscard = new Resources();
		int playerIndex = this.stateManager.getFacade().getPlayerIndex();
		p = this.stateManager.getClientModel().getPlayers()[playerIndex];
		int totalPlayerResources = p.getResources().getTotalCount();
		this.getDiscardView().setDiscardButtonEnabled(false);
		this.getDiscardView().setResourceMaxAmount(ResourceType.BRICK, p.getResources().getBrickCount());
		this.getDiscardView().setResourceMaxAmount(ResourceType.ORE, p.getResources().getOreCount());
		this.getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, p.getResources().getSheepCount());
		this.getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, p.getResources().getWheatCount());
		this.getDiscardView().setResourceMaxAmount(ResourceType.WOOD, p.getResources().getWoodCount());
		
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, p.hasBrick(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, p.hasOre(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, p.hasSheep(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, p.hasWheat(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, p.hasWood(), false);

		
		this.getDiscardView().setStateMessage("0/" + totalPlayerResources);
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		int playerIndex = this.stateManager.getFacade().getPlayerIndex();
		Player p = this.stateManager.getClientModel().getPlayers()[playerIndex];
		int playerResourceNum = p.getResources().getAmountOfSpecificResource(resource);
		//this.getDiscardView().
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		
	}

	@Override
	public void discard() {
		
		getDiscardView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

