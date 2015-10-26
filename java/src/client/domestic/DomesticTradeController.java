package client.domestic;

import java.util.Observable;

import shared.definitions.*;
import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import client.models.Player;
import client.models.Resources;
import client.models.TradeOffer;
import client.state.ActivePlayerState;
import client.state.IStateBase;
import client.state.InactivePlayerState;
import client.state.StateManager;
import client.state.trading.OfferingTradeState;
import client.state.trading.ReceivingTradeState;
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
	private boolean newDomestic;
	int tradingPlayerIndex;

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
		this.tradingPlayerIndex = -1;
		this.newDomestic = true;
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
		this.setPlayerToTradeWith(-1);
		this.tradeOffer = new Resources();
		this.stateManager.setState(new OfferingTradeState(this.stateManager.getFacade()));
		this.getTradeOverlay().setCancelEnabled(true);
		this.getTradeOverlay().setPlayerSelectionEnabled(true);
		setTradeButton();
		if(newDomestic) {
			Player[] players = this.stateManager.getClientModel().getPlayers();
			PlayerInfo[] tradePlayers = new PlayerInfo[3];
			int tradePlayersIndex = 0;
			for(int i=0; i < 4; i++) {
				if(players[i].getPlayerIndex() != this.stateManager.getFacade().getLocalPlayer().getPlayerIndex()) {
					tradePlayers[tradePlayersIndex++] = new PlayerInfo(players[i]);
				}
			}
	
			this.getTradeOverlay().setPlayers(tradePlayers);
			this.newDomestic = false;
		}
		this.localResources = getLocalResources();
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
				} else {
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
			}
		} else if (recieving) {
			tradeOffer.subtractOne(resource);
			offerCount = tradeOffer.getResourceCount(resource);
			if(offerCount == 0) {
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
			}
		}
		setTradeButton();
		System.out.println("Current Trade Offer: " + tradeOffer.toString());
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		int localCount = localResources.getResourceCount(resource);
		int offerCount = tradeOffer.getResourceCount(resource);
		if(sending) {  //Sending an offer makes the resource a negative number
			if( Math.abs(offerCount) < localCount) {
				tradeOffer.subtractOne(resource);
			
				if( localCount == (Math.abs(offerCount) + 1)) {
					getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
				} else {
					getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
				}
			}
		} else { //This means we're receiving. so we add the number
			tradeOffer.addOne(resource);
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		setTradeButton();
		System.out.println("Current Trade Offer: " + tradeOffer.toString());
	}

	@Override
	public void sendTradeOffer() {
		IStateBase state = this.stateManager.getState();
		getTradeOverlay().closeModal();
		int localPlayerIndex = this.stateManager.getFacade().getLocalPlayer().getPlayerIndex();
		TradeOffer newTrade = new TradeOffer(localPlayerIndex, tradingPlayerIndex, tradeOffer);
		state.sendTradeOffer(newTrade);
		this.stateManager.setState(new TradeOfferedWaitingState(this.stateManager.getFacade()));
		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		tradingPlayerIndex = playerIndex;
		setTradeButton();
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
	
	public boolean tradeIsValid() {
		//There needs to be at least ONE resource being sent and ONE resource being received for a trade to be valid
		if(tradeOffer.getBrickCount() < 0 || tradeOffer.getOreCount() < 0 || tradeOffer.getSheepCount() < 0 || tradeOffer.getWheatCount() < 0 || tradeOffer.getWoodCount() < 0) {
			if(tradeOffer.getBrickCount() > 0 || tradeOffer.getOreCount() > 0 || tradeOffer.getSheepCount() > 0 || tradeOffer.getWheatCount() > 0 || tradeOffer.getWoodCount() > 0){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean tradingIndexIsValid() {
		//There needs to be a valid player index for the trade to be valid
		if(tradingPlayerIndex == -1) {
			return false;
		}
		return true;
	}
	
	public void setTradeButton() {
		if(tradeIsValid()) {
			if(tradingIndexIsValid()) {
				this.getTradeOverlay().setTradeEnabled(true);
				this.getTradeOverlay().setStateMessage("Trade!");
			} else {
				this.getTradeOverlay().setTradeEnabled(false);
				this.getTradeOverlay().setStateMessage("Choose with whom you want to trade");
			}
		} else {
			this.getTradeOverlay().setTradeEnabled(false);
			this.getTradeOverlay().setStateMessage("Select the resources you want to trade");
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
		setTradeButton();
	}

	@Override
	public void cancelTrade() {
		this.setPlayerToTradeWith(-1);
		this.tradeOffer = new Resources();
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
		if(this.stateManager.getState() instanceof TradeOfferedWaitingState) {
			TradeOffer offer = this.stateManager.getClientModel().getTradeOffer();
			if(offer == null) {
				this.stateManager.setState(new ActivePlayerState(this.stateManager.getFacade())); //This may be completely unnecessary and maybe we should set them to ActivePlayerState here??
				getWaitOverlay().closeModal();
			}
		}
		if(this.stateManager.getState() instanceof InactivePlayerState) {
			TradeOffer offer = this.stateManager.getClientModel().getTradeOffer();	
			if(offer != null) {
				if(offer.getReceiver() == stateManager.getFacade().getPlayerIndex()) {
					this.stateManager.setState(new ReceivingTradeState(this.stateManager.getFacade()));
					getAcceptOverlay().setPlayerName(stateManager.getClientModel().getPlayers()[offer.getSender()].getName());
					addGetResources(offer);
					addGiveResources(offer);
					getAcceptOverlay().showModal();
				}
			}
		}
		if(this.stateManager.getState() instanceof ActivePlayerState) {
			TradeOffer offer = this.stateManager.getClientModel().getTradeOffer();

			if(offer != null) {
				if(offer.getSender() == stateManager.getFacade().getPlayerIndex()) {
					this.stateManager.setState(new TradeOfferedWaitingState(this.stateManager.getFacade()));
					if(!getWaitOverlay().isModalShowing()) {
						getWaitOverlay().showModal();
					}
				}
			} else {
				this.getTradeView().enableDomesticTrade(true);
			}
		}
		else {
			this.getTradeView().enableDomesticTrade(false);
		}
	}
	
	private void addGetResources(TradeOffer offer) {
		if(offer.getOreCount() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.ORE, Math.abs(offer.getOreCount()));
		}
		if(offer.getBrickCount() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.BRICK, Math.abs(offer.getBrickCount()));
		}
		if(offer.getSheepCount() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.SHEEP, Math.abs(offer.getSheepCount()));
		}
		if(offer.getWheatCount() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.WHEAT, Math.abs(offer.getWheatCount()));
		}
		if(offer.getWoodCount() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.WOOD, Math.abs(offer.getWoodCount()));
		}
	}
	
	private void addGiveResources(TradeOffer offer) {
		if(offer.getOreCount() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.ORE, Math.abs(offer.getOreCount()));
		}
		if(offer.getBrickCount() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.BRICK, Math.abs(offer.getBrickCount()));
		}
		if(offer.getSheepCount() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.SHEEP, Math.abs(offer.getSheepCount()));
		}
		if(offer.getWheatCount() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.WHEAT, Math.abs(offer.getWheatCount()));
		}
		if(offer.getWoodCount() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.WOOD, Math.abs(offer.getWoodCount()));
		}
	}

}

