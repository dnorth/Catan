package client.domestic;

import java.util.Observable;

import shared.definitions.*;
import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import client.models.Player;
import client.models.Resources;
import client.state.ActivePlayerState;
import client.state.IStateBase;
import client.state.InactivePlayerState;
import client.state.StateManager;
import client.state.trading.OfferingTradeState;
import client.state.trading.TradeOfferedWaitingState;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private StateManager stateManager;
	private Resources tradeOffer;
	private Resources localResources;
	private boolean sending;
	private boolean recieving;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay, StateManager stateManager) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		this.stateManager = stateManager;
		this.stateManager.addObserver(this);
		this.tradeOffer = new Resources();
		this.sending = false;
		this.recieving = false;
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {
		this.stateManager.setState(new OfferingTradeState(this.stateManager.getFacade()));
		this.getTradeOverlay().setCancelEnabled(true);
		this.getTradeOverlay().setPlayerSelectionEnabled(true);
		this.getTradeOverlay().setTradeEnabled(false);
		this.getTradeOverlay().setStateMessage("Select the resources you want to trade");
		Player[] players = this.stateManager.getClientModel().getPlayers();
		PlayerInfo[] tradePlayers = new PlayerInfo[3];
		int tradePlayersIndex = 0;
		for(int i = 0; i < 4; i++) {
			if(players[i].getPlayerIndex() != this.stateManager.getFacade().getLocalPlayer().getPlayerIndex()) {
				if(players[i].getResources().getTotalCount() > 0) { //Only want to trade with players who have cards
					tradePlayers[tradePlayersIndex++] = new PlayerInfo(players[i]);
				}
			}
		}
		this.localResources = getLocalResources();
		this.getTradeOverlay().setPlayers(tradePlayers);
		getTradeOverlay().showModal();
	}
	
	private Resources getLocalResources() {
		int gameIndex = stateManager.getFacade().getPlayerIndex();
		return stateManager.getClientModel().getPlayers()[gameIndex].getResources();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		int offerCount = tradeOffer.getResourceCount(resource);
		if(sending) {
			if(offerCount < 0) {
				tradeOffer.addOne(resource);
				
				if( (offerCount + 1) == 0) {
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
				}
			}
		} else if (recieving) {
			if(offerCount == 0) {
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
			}
		}
		
		System.out.println("Current Trade Offer: " + tradeOffer.toString());
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		int localCount = localResources.getResourceCount(resource);
		int offerCount = tradeOffer.getResourceCount(resource);
		if(sending) {  //Sending an offer makes the resource a negative number
			if( offerCount < localCount) {
				tradeOffer.subtractOne(resource);
			
				if( localCount == (offerCount + 1)) {
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				}
			}
		} else { //This means we're receiving. so we add the number
			tradeOffer.addOne(resource);
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		
		System.out.println("Current Trade Offer: " + tradeOffer.toString());
	}

	@Override
	public void sendTradeOffer() {
		IStateBase state = this.stateManager.getState();
		getTradeOverlay().closeModal();
		state.sendTradeOffer();
		this.stateManager.setState(new TradeOfferedWaitingState(this.stateManager.getFacade()));
		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		IStateBase state = this.stateManager.getState();
		state.setPlayerToTradeWith(playerIndex);
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		tradeOffer.unsetResource(resource);
		getTradeOverlay().setResourceAmount(resource, "0");
		sending = false;
		recieving = true;
		int offerCount = tradeOffer.getResourceCount(resource);
		if( offerCount == 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		tradeOffer.unsetResource(resource);
		getTradeOverlay().setResourceAmount(resource, "0");
		sending = true;
		recieving = false;
		int localCount = localResources.getResourceCount(resource);
		if( localCount == 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
		} else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
	}

	@Override
	public void unsetResource(ResourceType resource) {
		tradeOffer.unsetResource(resource);
	}

	@Override
	public void cancelTrade() {
		this.stateManager.getFacade().cancelTradeOffer();		
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		IStateBase state = this.stateManager.getState();
		state.acceptTrade(willAccept);
		this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade()));
		getAcceptOverlay().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(this.stateManager.getState() instanceof ActivePlayerState) {
			System.out.println("DOMESTIC TRADE BUTTON ENABLED");
			this.getTradeView().enableDomesticTrade(true);
		}
		else {
			this.getTradeView().enableDomesticTrade(false);
		}
		if(this.stateManager.getState() instanceof TradeOfferedWaitingState) {
			
			if(this.stateManager.getClientModel().getTradeOffer() == null) {
				this.stateManager.setState(new InactivePlayerState(this.stateManager.getFacade())); //This may be completely unnecessary and maybe we should set them to ActivePlayerState here??
			}
			else {
				
			}
		}
	}

}

