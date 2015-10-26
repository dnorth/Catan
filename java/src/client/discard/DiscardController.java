package client.discard;

import java.util.Observable;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import client.models.Player;
import client.models.Resources;
import client.state.IStateBase;
import client.state.StateManager;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	private StateManager stateManager;
	private Resources toDiscard;
	private Player p;
	private boolean alreadyDiscarding;
	private boolean waiting;
	
	private int maxBrick;
	private int maxOre;
	private int maxSheep;
	private int maxWheat;
	private int maxWood;
	
	private int currBrick;
	private int currOre;
	private int currSheep;
	private int currWheat;
	private int currWood;
	
	private int totalToDiscard;
	private int currTotal;
	
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
		alreadyDiscarding = false;
		waiting = false;
	}
	
	private void initialize() {
		toDiscard = new Resources();
		int playerIndex = this.stateManager.getFacade().getPlayerIndex();
		p = this.stateManager.getClientModel().getPlayers()[playerIndex];
		int totalPlayerResources = p.getResources().getTotalCount();
		this.getDiscardView().setDiscardButtonEnabled(false);
		
		maxBrick = p.getResources().getBrickCount();
		maxOre = p.getResources().getOreCount();
		maxSheep = p.getResources().getSheepCount();
		maxWheat = p.getResources().getWheatCount();
		maxWood = p.getResources().getWoodCount();
		
		currBrick = 0;
		currOre = 0;
		currSheep = 0;
		currWheat = 0;
		currWood = 0;
		
		totalToDiscard = totalPlayerResources/2;
		currTotal = 0;
		
		this.getDiscardView().setResourceMaxAmount(ResourceType.BRICK, maxBrick);
		this.getDiscardView().setResourceMaxAmount(ResourceType.ORE, maxOre);
		this.getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, maxSheep);
		this.getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, maxWheat);
		this.getDiscardView().setResourceMaxAmount(ResourceType.WOOD, maxWood);

		this.getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.ORE, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, 0);

		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, p.hasBrick(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, p.hasOre(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, p.hasSheep(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, p.hasWheat(), false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, p.hasWood(), false);
			
		this.getDiscardView().setStateMessage("0/" + totalToDiscard);
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource) {
		case BRICK:
			if(++currBrick < maxBrick)this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, currBrick); //just to clarify, the third line in each switch statement is NOT supposed to be a part of the "else"
			break;
		case ORE:
			if(++currOre < maxOre) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, currOre);
			break;
		case SHEEP:
			if(++currSheep < maxSheep) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, currSheep);
			break;
		case WHEAT:
			if(++currWheat < maxWheat) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, currWheat);
			break;
		case WOOD:
			if(++currWood < maxWood) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, currWood);
			break;
		}
		
		if(++currTotal == totalToDiscard) {
			if (currBrick > 0)this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
			
			if (currOre > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
			
			if (currSheep > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
			
			if (currWheat > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
			
			if (currWood > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
			
			this.getDiscardView().setDiscardButtonEnabled(true);
		}
		else {
			this.getDiscardView().setDiscardButtonEnabled(false);
		}
		this.getDiscardView().setStateMessage(currTotal + "/" + totalToDiscard);	
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		switch(resource) {
		case BRICK:
			if(--currBrick > 0)this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, currBrick); //just to clarify, the third line in each switch statement is NOT supposed to be a part of the "else"
			break;
		case ORE:
			if(--currOre > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, currOre);
			break;
		case SHEEP:
			if(--currSheep > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, currSheep);
			break;
		case WHEAT:
			if(--currWheat > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, currWheat);
			break;
		case WOOD:
			if(--currWood > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, currWood);
			break;
		}
		
		this.getDiscardView().setDiscardButtonEnabled(false);
		this.getDiscardView().setStateMessage(--currTotal + "/" + totalToDiscard);

		this.updateIncreasability();
	}
	
	private void updateIncreasability() {
		boolean decrease = false;
		boolean increase = false;
		if (currTotal < totalToDiscard) {
			if(currBrick < maxBrick) increase = true;
			else increase = false;
			if(currBrick > 0) decrease = true;
			else decrease = false;
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, increase, decrease);
			
			if(currOre < maxOre) increase = true;
			else increase = false;
			if(currOre > 0) decrease = true;
			else decrease = false;
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, increase, decrease);
			
			if(currSheep < maxSheep) increase = true;
			else increase = false;
			if(currSheep > 0) decrease = true;
			else decrease = false;
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, increase, decrease);
			
			if(currWheat < maxWheat) increase = true;
			else increase = false;
			if(currWheat > 0) decrease = true;
			else decrease = false;
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, increase, decrease);
			
			if(currWood < maxWood) increase = true;
			else increase = false;
			if(currWood > 0) decrease = true;
			else decrease = false;
			this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, increase, decrease);
		}
	}

	@Override
	public void discard() {
		IStateBase state = this.stateManager.getState();
		toDiscard.setBrick(currBrick);
		toDiscard.setOre(currOre);
		toDiscard.setSheep(currSheep);
		toDiscard.setWheat(currWheat);
		toDiscard.setWood(currWood);
		alreadyDiscarding = false;
		waiting = true;
		state.discard(toDiscard);
		getDiscardView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (stateManager.getClientModel().getTurnTracker().getStatus().equals("Discarding")) {
			if (!alreadyDiscarding && !waiting) {
				alreadyDiscarding = true;
				initialize();
				this.getDiscardView().showModal();				
			}
			else if (waiting) {
				waitView.setMessage("Waiting for other players to discard");
				waitView.showModal();				
			}
		}
		else {
			waiting = false;
			if (waitView.isModalShowing()) {
				waitView.closeModal();
			}
		}
	}

}

